package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.CustomMappers.BookingRowMapper;
import com.AirlineManagement.Airline_Management_System.CustomMappers.FlightRowMapper;
import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Entities.Flight;
import com.AirlineManagement.Airline_Management_System.Entities.Passenger;
import com.AirlineManagement.Airline_Management_System.Entities.Seat;
import com.AirlineManagement.Airline_Management_System.Entities.Ticket;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService{
    @Autowired
    JdbcTemplate template;
    @Override
    public List<Ticket> getTickets(String username) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "Select p.id From Passengers p JOIN Bookings b ON p.booking_id = b.id WHERE b.username = ?";
        
        List<Integer> passengers_ids = template.queryForList(sql, new Object[]{username}, Integer.class);
        sql = "Select * From Passengers WHERE id = ?";
        
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < passengers_ids.size(); i++) {
            try {
                passengers.add(template.queryForObject(sql, new Object[]{passengers_ids.get(i)}, new BeanPropertyRowMapper<>(Passenger.class)));
            } catch (EmptyResultDataAccessException e) {
                continue;
            }
        }

        for (Passenger passenger : passengers) {
            try {
                String seat_no = template.queryForObject("SELECT seat_no FROM Tickets WHERE passenger_id = ?", String.class, passenger.getId());
                Seat seat = template.queryForObject("SELECT * FROM Seats WHERE seat_no = ?", new Object[]{seat_no}, new BeanPropertyRowMapper<>(Seat.class));
                Integer flight_id = template.queryForObject("SELECT flight_id FROM Tickets WHERE passenger_id = ?", Integer.class, passenger.getId());
                Flight flight = template.queryForObject("SELECT f.id, f.arrival, f.departure, f.to_location, f.from_location, f.price, f.booked_seats, f.total_seats, f.duration, f.status, a.id, a.name, ac.id, ac.model, ac.status, ac.seats FROM flights f JOIN airlines a ON f.airline_id = a.id JOIN aircrafts ac ON f.aircraft_id = ac.id WHERE f.id = ?", new Object[]{flight_id}, new FlightRowMapper());
                Ticket ticket = template.queryForObject("SELECT * FROM Tickets WHERE passenger_id = ?", new Object[]{passenger.getId()}, new BeanPropertyRowMapper<>(Ticket.class));
                ticket.setSeat(seat);
                ticket.setFlight(flight);
                ticket.setPassenger(passenger);
                tickets.add(ticket);
            } catch (EmptyResultDataAccessException e) {
                continue;
            }
        }
        return tickets;
}


    @Override
    public void createTicket(Booking booking) {
        approveBooking(booking.getId());
        booking = setBooking(booking.getId());
        List<Passenger> passengers = getPassengers(booking.getId());
        Flight flight = getFlight(booking.getId());
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
        sql = "UPDATE Payments SET status = 'Confirmed' WHERE booking_id = ?";
        template.update(sql, booking.getId());
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
        String sql = "Select flight_id From Bookings WHERE id = ?";
        Long flight_id = template.queryForObject(sql, new Object[]{id}, Long.class);
        sql = "SELECT f.id, f.arrival, f.departure, f.to_location, f.from_location, f.price, f.booked_seats, f.total_seats, f.duration, f.status, a.id, a.name, ac.id, ac.model, ac.status, ac.seats FROM flights f JOIN airlines a ON f.airline_id = a.id JOIN aircrafts ac ON f.aircraft_id = ac.id WHERE f.id = ?";
        return template.queryForObject(sql, new Object[]{flight_id}, new FlightRowMapper());
    }
    private Booking setBooking(long id){
        String sql = "Select * From Bookings WHERE id = ?";
        return template.queryForObject(sql, new Object[]{id}, new BookingRowMapper());
    }
    private List<Seat> getSeats(long id, int size){
        String sql = "Select * From Seats WHERE status = 'Reserved' AND aircraft_id = ? LIMIT ?";
        return template.query(sql, new Object[]{id, size}, new BeanPropertyRowMapper<>(Seat.class));
    }

}
