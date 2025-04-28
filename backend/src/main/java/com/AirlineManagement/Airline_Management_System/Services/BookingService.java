package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.DTOs.BookingData;
import com.AirlineManagement.Airline_Management_System.Entities.Booking;

import java.util.List;

import org.springframework.web.bind.annotation.*;

public interface BookingService {
    public List<Booking> get(String username);
    public Booking create(BookingData data);
}
