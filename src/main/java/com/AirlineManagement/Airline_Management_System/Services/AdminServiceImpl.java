package com.AirlineManagement.Airline_Management_System.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.AirlineManagement.Airline_Management_System.Entities.Flight;
import com.AirlineManagement.Airline_Management_System.CustomMappers.FlightRowMapper;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    JdbcTemplate template;

    @Override
    public List<Flight> getAllFlights() {
        String sql = "SELECT f.id, f.arrival, f.departure, f.to_location, f.from_location, f.price, f.booked_seats, f.total_seats, f.duration, f.status, a.id, a.name, ac.id, ac.model, ac.status, ac.seats FROM flights f JOIN airlines a ON f.airline_id = a.id JOIN aircrafts ac ON f.aircraft_id = ac.id WHERE f.status NOT IN ('Cancelled', 'Landed')   OR (f.status IN ('Cancelled', 'Landed') AND f.departure >= NOW() - INTERVAL 1 MONTH) ORDER BY f.id ASC;";
        List<Flight> flights = template.query(sql, new FlightRowMapper());
        return flights;
    }
}
