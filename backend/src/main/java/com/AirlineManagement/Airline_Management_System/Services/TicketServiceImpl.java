package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.CustomMappers.BookingRowMapper;
import com.AirlineManagement.Airline_Management_System.CustomMappers.FlightRowMapper;
import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Entities.Flight;
import com.AirlineManagement.Airline_Management_System.Entities.Passenger;
import com.AirlineManagement.Airline_Management_System.Entities.Seat;
import com.AirlineManagement.Airline_Management_System.Entities.Ticket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService{
    @Autowired
    JdbcTemplate template;
    @Override
    public Ticket get(Long id) {
        return null;
    }

    @Override
    public Ticket getSpecific(Long flightId, Long passengerId) {
        return null;
    }

    @Override
    public void createTicket(Booking booking) {
        approveBooking(booking.getId());
        booking = setBooking(booking.getId());
        List<Passenger> passengers = getPassengers(booking.getId());
        Flight flight = getFlight(booking.getFlight().getId());
        booking.setFlight(flight);
        List<Seat> seats = getSeats(flight.getAircraft().getId(), passengers.size());
        String sql = "UPDATE Seats SET status = 'Booked' WHERE seat_no = ?";
        for (int i = 0; i < seats.size(); i++) {
            template.update(sql, seats.get(i).getSeatNo());
        }
        for (int i = 0; i < passengers.size(); i++) {
            Ticket ticket = new Ticket();
            ticket.setPassenger(passengers.get(i));
            ticket.setSeat(seats.get(i));
            ticket.setFlight(flight);
            ticket.setStatus("Valid");
            insertTicket(ticket);
        }
    }
    private void insertTicket(Ticket ticket){
        String sql = "INSERT INTO Tickets (seat_no, passenger_id, flight_id, status) VALUES(?, ?, ?, ?)";
        template.update(sql, ticket.getSeat().getSeatNo(), ticket.getPassenger().getId(), ticket.getFlight().getId(), ticket.getStatus());
    }
    private void approveBooking(long id){
        String sql = "UPDATE Bookings SET status = 'Approved' WHERE id = ?";
        template.update(sql, id);
    }
    private List<Passenger> getPassengers(long id){
        String sql = "Select * From Passengers WHERE booking_id = ?";
        return template.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Passenger.class));
    }
    private Flight getFlight(long id){
        String sql = "SELECT f.id, f.arrival, f.departure, f.to_location, f.from_location, f.price, f.booked_seats, f.total_seats, f.duration, f.status, a.id, a.name, ac.id, ac.model, ac.status, ac.seats FROM flights f JOIN airlines a ON f.airline_id = a.id JOIN aircrafts ac ON f.aircraft_id = ac.id WHERE f.id = ?";
        return template.queryForObject(sql, new Object[]{id}, new FlightRowMapper());
    }
    private Booking setBooking(long id){
        String sql = "Select * From Bookings WHERE id = ?";
        return template.queryForObject(sql, new Object[]{id}, new BookingRowMapper());
    }
    private List<Seat> getSeats(long id, int size){
        String sql = "Select * From Seats WHERE status = 'Reserved' AND aircraft_id = ? LIMIT ?";
        return template.query(sql, new Object[]{id, size}, new BeanPropertyRowMapper<>(Seat.class));
    }

    @Override
    public void rejected(long id) {
        String sql = "UPDATE Bookings SET status = 'Cancelled' WHERE id = ?";
        template.update(sql, id);
        Booking booking = setBooking(id);
        sql = "Select COUNT(id) From Passengers WHERE booking_id = ?";
        Integer size = template.queryForObject(sql, Integer.class, booking.getId());
        Flight flight = getFlight(booking.getFlight().getId());
        booking.setFlight(flight);
        List<Seat> seats = getSeats(flight.getAircraft().getId(), size);
        sql = "UPDATE Seats SET status = 'Available' WHERE seat_no = ?";
        for (int i = 0; i < seats.size(); i++) {
            template.update(sql, seats.get(i).getSeatNo());
        }
        sql = "UPDATE Flights SET booked_seats = ? WHERE id = ?";
        template.update(sql, flight.getBookedSeats()-size, flight.getId());
    }

    @Override
    public void cancelTicket(Ticket ticket) {
        String sql = "Select ";
    }
}
