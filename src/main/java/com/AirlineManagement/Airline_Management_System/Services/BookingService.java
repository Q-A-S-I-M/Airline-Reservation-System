package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import org.springframework.web.bind.annotation.*;

public interface BookingService {
    public Booking get(Long id);
    public Booking create(Booking booking);
    public Booking cancel(Long id);
}
