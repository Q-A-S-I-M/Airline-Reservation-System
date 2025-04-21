package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.CustomMappers.FlightRowMapper;
import com.AirlineManagement.Airline_Management_System.DTOs.FlightCreation;
import com.AirlineManagement.Airline_Management_System.Entities.AirCraft;
import com.AirlineManagement.Airline_Management_System.Entities.Airline;
import com.AirlineManagement.Airline_Management_System.Entities.Flight;
import com.AirlineManagement.Airline_Management_System.Misc.FlightFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Date;

@Service
public class FlightServiceImpl implements FlightService{
    @Autowired
    JdbcTemplate template;
    @Override
    public List<Flight> getAllFlights() {
        String sql = "SELECT f.id, f.arrival, f.departure, f.to_location, f.from_location, f.price, f.booked_seats, f.total_seats, f.duration, f.status, a.id, a.name, ac.id, ac.model, ac.status, ac.seats FROM flights f JOIN airlines a ON f.airline_id = a.id JOIN aircrafts ac ON f.aircraft_id = ac.id WHERE f.status NOT IN ('Cancelled', 'Landed')   OR (f.status IN ('Cancelled', 'Landed') AND f.departure >= NOW() - INTERVAL 1 MONTH) ORDER BY f.id ASC;";
        List<Flight> flights = template.query(sql, new FlightRowMapper());
        return flights;
    }

    @Override
    public Flight get(Long id) {
        String sql = "Select * From Flights Where id = ?";
        Flight flight = template.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Flight.class));
        return flight;
    }

    @Override
    public void create(Flight flight) {
        String sql = "INSERT INTO Flights (from_location, to_location, departure, arrival, booked_seats, total_seats, status, price, duration, airline_id, aircraft_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        flight.setDuration(getDurationBetween(flight.getDeparture(), flight.getArrival()));
        
        template.update( sql, flight.getFromLocation(), flight.getToLocation(), flight.getDeparture(), flight.getArrival(), 0, flight.getAircraft().getSeats(), "Scheduled", flight.getPrice(), flight.getDuration(), flight.getAirline().getId(), flight.getAircraft().getId());
        sql = "UPDATE aircrafts SET status = 'Assigned' WHERE id = ?";
        template.update(sql, flight.getAircraft().getId());
        return;
    }
    private String getDurationBetween(Date departure, Date arrival){
        Instant dep = departure.toInstant();
        Instant arr = arrival.toInstant();

        Duration duration = Duration.between(dep, arr);

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        return String.format("%02d:%02d", hours, minutes);
    }

    @Override
    public Flight update(Long id, Flight flight) {
        return null;
    }

    @Override
    public List<Flight> search(FlightFilter filter) {
        return null;
    }
    @Override
    public void updateStatus(Long id, String status) {
        String sql = "UPDATE flights SET status = ? WHERE id = ?";
        template.update(sql, status, id);
        if(status.equals("Landed")||status.equals("Cancelled")){
            sql = "UPDATE aircrafts SET status = 'Unassigned' WHERE id = (SELECT aircraft_id FROM flights WHERE id = ?)";
            template.update(sql, id);
        }
    }

    @Override
    public FlightCreation getData() {
        String sql = "SELECT * FROM Airlines";
        List<Airline> airlines = template.query(sql, new BeanPropertyRowMapper<>(Airline.class));
        sql = "SELECT * FROM Aircrafts WHERE status = 'Unassigned'";
        List<AirCraft> aircrafts = template.query(sql, new BeanPropertyRowMapper<>(AirCraft.class));
        return new FlightCreation(airlines, aircrafts);
    }
}
