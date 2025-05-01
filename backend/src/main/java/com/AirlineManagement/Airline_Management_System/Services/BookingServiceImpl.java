package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.CustomMappers.BookingRowMapper;
import com.AirlineManagement.Airline_Management_System.CustomMappers.FlightRowMapper;
import com.AirlineManagement.Airline_Management_System.DTOs.BookingData;
import com.AirlineManagement.Airline_Management_System.Entities.AirCraft;
import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Entities.Flight;
import com.AirlineManagement.Airline_Management_System.Entities.Passenger;
import com.AirlineManagement.Airline_Management_System.Entities.Seat;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingServiceImpl implements BookingService{
    @Autowired
    JdbcTemplate template;
    @Autowired
    PassengerService passengerService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    NotificationService notificationService;
    @Override
    public List<Booking> get(String username) {
        String sql = "Select * From Bookings WHERE username = ? ORDER BY timestamp DESC";
        List<Booking> bookings = template.query(sql, new Object[]{username}, new BookingRowMapper());
        return bookings;
    }
    @Transactional
    @Override
    public Booking create(BookingData data) {
        List<Passenger> passengers = data.getPassengers();
        Booking booking = data.getBooking();
        booking.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        booking.setPaymentDeadline(Timestamp.valueOf(LocalDateTime.now().plus(1, ChronoUnit.DAYS)));
        booking.setAmount(booking.getFlight().getPrice()*passengers.size());
        booking.setStatus("Pending");
        Long id = insertBooking(booking);
        booking.setId(id);
        for (int i = 0; i < passengers.size(); i++) {
            passengers.get(i).setBooking(booking);
            passengerService.create(passengers.get(i));
        }
        reserveSeats(booking.getFlight().getAircraft(), passengers.size());
        int seat = booking.getFlight().getBookedSeats();
        booking.getFlight().setBookedSeats(seat+passengers.size());
        String sql = "UPDATE Flights SET booked_seats = ? WHERE id = ?";
        template.update(sql, booking.getFlight().getBookedSeats(), booking.getFlight().getId());
        paymentService.create(booking);
        return booking;
    }

    private void reserveSeats(AirCraft aircraft, int seats) {
        String sql = "SELECT seat_no FROM Seats WHERE status = 'Available' AND aircraft_id = ? LIMIT ?";
        List<String> ids= template.queryForList(sql, String.class, aircraft.getId(), seats);
        sql = "UPDATE Seats SET status = 'Reserved' WHERE seat_no = ?";
        for (int i = 0; i < ids.size(); i++) {
            template.update(sql, ids.get(i));
        }
    }

    Long insertBooking(Booking booking){
        String sql = "INSERT INTO Bookings (timestamp, payment_deadline, status, amount, flight_id, username) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, (java.sql.Timestamp) booking.getTimestamp());
            ps.setTimestamp(2, (java.sql.Timestamp) booking.getPaymentDeadline());
            ps.setString(3, booking.getStatus());
            ps.setDouble(4, booking.getAmount());
            ps.setLong(5, booking.getFlight().getId());
            ps.setString(6, booking.getUser().getUsername());
            return ps;
        }, keyHolder);
        return ((BigInteger) keyHolder.getKey()).longValue();
    }
    @Transactional
    @Override
    public void rejected(Booking booking) {
        cancelBooking(booking.getId());
        booking = getBooking(booking.getId());
        List<Passenger> passengers = getPassengers(booking.getId());
        Flight flight = getFlight(booking.getFlight().getId());
        booking.setFlight(flight);
        List<Seat> seats = getSeats(flight.getAircraft().getId(), passengers.size());
        String sql = "UPDATE Seats SET status = 'Available' WHERE seat_no = ?";
        for (int i = 0; i < seats.size(); i++) {
            template.update(sql, seats.get(i).getSeatNo());
        }
    }
    private void cancelBooking(long id){
        String sql = "UPDATE Bookings SET status = 'Cancelled' WHERE id = ?";
        template.update(sql, id);
    }
    private List<Passenger> getPassengers(long id){
        String sql = "Select * From Passengers WHERE booking_id = ?";
        return template.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Passenger.class));
    }
    private Flight getFlight(long id){
        String sql = "Select flight_id From Bookings WHERE id = ?";
        Long flight_id = template.queryForObject(sql, new Object[]{id}, Long.class);        
        sql = "SELECT f.id, f.arrival, f.departure, f.to_location, f.from_location, f.price, f.booked_seats, f.total_seats, f.duration, f.status, a.id, a.name, ac.id, ac.model, ac.status, ac.seats FROM flights f JOIN airlines a ON f.airline_id = a.id JOIN aircrafts ac ON f.aircraft_id = ac.id WHERE f.id = ?";
        return template.queryForObject(sql, new Object[]{flight_id}, new FlightRowMapper());
    }
    private Booking getBooking(long id){
        String sql = "Select * From Bookings WHERE id = ?";
        return template.queryForObject(sql, new Object[]{id}, new BookingRowMapper());
    }
    private List<Seat> getSeats(long id, int size){
        String sql = "Select * From Seats WHERE status = 'Reserved' AND aircraft_id = ? LIMIT ?";
        return template.query(sql, new Object[]{id, size}, new BeanPropertyRowMapper<>(Seat.class));
    }
    @Transactional
    @Override
    public void cancelTickets(Booking booking) {
        cancelBooking(booking.getId());
        booking = getBooking(booking.getId());
        booking.setFlight(getFlight(booking.getId()));
        List<Passenger> passengers = getPassengers(booking.getId());
        String sql = "UPDATE Tickets SET status = 'Invalid' WHERE passenger_id = ?";
        for (int i = 0; i < passengers.size(); i++) {
            template.update(sql, passengers.get(i).getId());
        }
        List<String> seats = new ArrayList<>();
        sql = "Select seat_no From Tickets WHERE passenger_id = ?";
        for (int i = 0; i < passengers.size(); i++) {
            seats.add(template.queryForObject(sql, String.class, passengers.get(i).getId()));
        }
        sql = "UPDATE Seats SET status = 'Available' WHERE seat_no = ?";
        for (int i = 0; i < seats.size(); i++) {
            template.update(sql, seats.get(i));
        }
    }

    @Override
    @Scheduled(fixedRate = 60*1000)
    public void checkDeadline() {
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

        String sql = "SELECT * FROM Bookings WHERE payment_deadline < ? AND status != 'Deadline crossed'";
        List<Booking> bookings = template.query(sql, new Object[]{now}, new BookingRowMapper());

        for (int i = 0; i < bookings.size(); i++) {
            if(bookings.get(i).getPaymentDeadline().before(now) && "Pending".equals(bookings.get(i).getStatus())){
                sql = "UPDATE Bookings SET status = 'Deadline crossed' WHERE id = ?";
                template.update(sql, bookings.get(i).getId());

                List<Passenger> passengers = getPassengers(bookings.get(i).getId());

                Flight flight = getFlight(bookings.get(i).getFlight().getId());
                bookings.get(i).setFlight(flight);

                List<Seat> seats = getSeats(flight.getAircraft().getId(), passengers.size());

                sql = "UPDATE Seats SET status = 'Available' WHERE seat_no = ?";
                for (int j = 0; j < seats.size(); j++) {
                    template.update(sql, seats.get(j).getSeatNo());
                }
                notificationService.createDeadlineCrossed(bookings.get(i));
            }
        }
    }
}
