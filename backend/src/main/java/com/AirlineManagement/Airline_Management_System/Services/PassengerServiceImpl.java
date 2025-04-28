package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.Passenger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PassengerServiceImpl implements PassengerService{
    @Autowired
    JdbcTemplate template;
    @Override
    public void create(Passenger passenger) {
        String sql = "INSERT INTO Passengers (contact, dob, first_name, last_name, booking_id) VALUES (?, ?, ?, ?, ?)";
        template.update(sql, passenger.getContact(), passenger.getDob(), passenger.getFirstName(), passenger.getLastName(), passenger.getBooking().getId());
    }

    @Override
    public Passenger get(Long id) {
        return null;
    }

    @Override
    public Passenger update(Long id, Passenger updated) {
        return null;
    }
}
