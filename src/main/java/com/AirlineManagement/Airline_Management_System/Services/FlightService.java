package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.Flight;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface FlightService {
    public List<Flight> getAll();
    public Flight get(Long id);
    public Flight create(Flight flight);
    public Flight update(Long id, Flight flight);
}
