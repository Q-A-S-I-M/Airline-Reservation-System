package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.Airline;

import java.util.List;

public interface AirlineService {
    public List<Airline> getAll();
    public Airline get(Long id);
    public Airline create(Airline airline);
}
