package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.DTOs.BookingData;
import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Services.BookingService;
import com.AirlineManagement.Airline_Management_System.Services.NotificationService;
import com.AirlineManagement.Airline_Management_System.Services.PaymentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    NotificationService notificationService;

    @GetMapping("/{username}")
    public List<Booking> getBookings(@PathVariable String username) {
        return bookingService.get(username);
    }

    @PostMapping("/create")
    public Booking createBooking(@RequestBody BookingData data) {
        return bookingService.create(data);
    }
    @PostMapping("/payment")
    public void makePayment(@RequestBody Booking booking){
        paymentService.process(booking.getId());
        notificationService.createBookingApproval(booking);
    }
    @PutMapping("/cancel")
    public void cancelBooking(@RequestBody Booking booking){
        bookingService.rejected(booking);
    }
    @PostMapping("/cancel-request")
    public void cancelRequest(@RequestBody Booking booking){
        notificationService.createCancellationApproval(booking);
    }
}
