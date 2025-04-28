package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.DTOs.BookingData;
import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Repositories.BookingRepository;
import com.AirlineManagement.Airline_Management_System.Services.BookingService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/{username}")
    public List<Booking> getBookings(@PathVariable String username) {
        return bookingService.get(username);
    }

    @PostMapping
    public Booking createBooking(@RequestBody BookingData data) {
        return bookingService.create(data);
    }
}
