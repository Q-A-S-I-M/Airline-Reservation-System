package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.Flight;
import com.AirlineManagement.Airline_Management_System.Misc.FlightFilter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface FlightService {
    public List<Flight> getAll();
    public Flight get(Long id);
    public void create(Flight flight);
    public Flight update(Long id, Flight flight);
    public List<Flight> search(FlightFilter filter);
}
