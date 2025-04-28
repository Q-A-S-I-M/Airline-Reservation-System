package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.AirCraft;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface AirCraftService {
    public List<AirCraft> getAll();
    public AirCraft get(Long id);
    public AirCraft create(AirCraft aircraft);
    public void delete(Long id);
}
