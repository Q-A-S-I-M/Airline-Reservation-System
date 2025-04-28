package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.Passenger;

import java.util.List;

import org.springframework.web.bind.annotation.*;

public interface PassengerService {
    public void create(Passenger passenger);
    public Passenger get(Long id);
    public Passenger update(Long id, Passenger updated);
}
