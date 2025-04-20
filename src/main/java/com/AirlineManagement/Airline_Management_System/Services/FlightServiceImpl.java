package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.AirCraft;
import com.AirlineManagement.Airline_Management_System.Entities.Airline;
import com.AirlineManagement.Airline_Management_System.Entities.Flight;
import com.AirlineManagement.Airline_Management_System.Misc.FlightFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService{
    @Autowired
    JdbcTemplate template;
    @Override
    public List<Flight> getAll() {
        String sql = "Select * From Flights";
        List<Flight> flights = template.query(sql, new BeanPropertyRowMapper<>(Flight.class));
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
        String sql = "SELECT * FROM Aircrafts WHERE id = ?";
        AirCraft aircraft = template.queryForObject(sql,new Object[]{flight.getAircraft().getId()},new BeanPropertyRowMapper<>(AirCraft.class));

        sql = "SELECT * FROM Airlines WHERE id = ?";
        Airline airline = template.queryForObject(sql, new Object[]{flight.getAirline().getId()}, new BeanPropertyRowMapper<>(Airline.class));
        flight.setAircraft(aircraft);
        flight.setAirline(airline);

        sql = "INSERT INTO Flights (from_location, to_location, departure, arrival, booked_seats, total_seats, status, price, duration, airline_id, aircraft_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        template.update( sql, flight.getFromLocation(), flight.getToLocation(), flight.getDeparture(), flight.getArrival(), 0, flight.getAircraft().getSeats(), "Scheduled", flight.getPrice(), flight.getAirline().getId(), flight.getAircraft().getId());
        return;
    }

    @Override
    public Flight update(Long id, Flight flight) {
        return null;
    }

    @Override
    public List<Flight> search(FlightFilter filter) {
        return null;
    }
}
