package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.DTOs.FlightCreation;
import com.AirlineManagement.Airline_Management_System.Entities.Flight;
import com.AirlineManagement.Airline_Management_System.DTOs.FlightFilter;
import com.AirlineManagement.Airline_Management_System.DTOs.Location;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface FlightService {
    public Flight get(Long id);
    public void create(Flight flight);
    public List<Flight> search(FlightFilter filter);
    public List<Flight> getAllFlights();
    public void updateStatus(Long id, String status);
    public FlightCreation getData();
    public Location getlocations();
}
