package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.CustomMappers.BookingRowMapper;
import com.AirlineManagement.Airline_Management_System.DTOs.BookingData;
import com.AirlineManagement.Airline_Management_System.Entities.AirCraft;
import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Entities.Passenger;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService{
    @Autowired
    JdbcTemplate template;
    @Autowired
    PassengerService passengerService;
    @Autowired
    PaymentService paymentService;
    @Override
    public List<Booking> get(String username) {
        String sql = "Select * From Bookings WHERE username = ?";
        List<Booking> bookings = template.query(sql, new Object[]{username}, new BookingRowMapper());
        return bookings;
    }

    @Override
    public Booking create(BookingData data) {
        List<Passenger> passengers = data.getPassengers();
        Booking booking = data.getBooking();
        LocalDateTime localDateTime = LocalDateTime.now();
        Date now = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        booking.setTimestamp(now);
        booking.setPaymentDeadline(Date.from(localDateTime.plusDays(1).atZone(ZoneId.systemDefault()).toInstant()));
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
            ps.setDate(1, (java.sql.Date) booking.getTimestamp());
            ps.setDate(2, (java.sql.Date) booking.getPaymentDeadline());
            ps.setString(3, booking.getStatus());
            ps.setDouble(4, booking.getAmount());
            ps.setLong(5, booking.getFlight().getId());
            ps.setString(6, booking.getUser().getUsername());
            return ps;
        }, keyHolder);
        return (Long) keyHolder.getKey();
    }

    
}
