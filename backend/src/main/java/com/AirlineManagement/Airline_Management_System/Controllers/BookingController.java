package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.DTOs.BookingData;
import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Services.BookingService;
import com.AirlineManagement.Airline_Management_System.Services.NotificationService;
import com.AirlineManagement.Airline_Management_System.Services.PaymentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    public ResponseEntity<?> getBookings(@PathVariable String username) {
        try {
            List<Booking> bookings = bookingService.get(username);
            return ResponseEntity.ok(bookings);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to retrieve bookings for user.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while fetching bookings.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody BookingData data) {
        try {
            Booking booking = bookingService.create(data);
            return ResponseEntity.ok(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to create booking. Please check the provided data.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred during booking creation.");
        }
    }
    @Transactional
    @PostMapping("/payment")
    public ResponseEntity<?> makePayment(@RequestBody Booking booking) {
        try {
            paymentService.process(booking.getId());
            notificationService.createBookingApproval(booking);
            return ResponseEntity.ok("Payment processed and booking approval notification sent.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Payment failed or booking data is invalid.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred during payment.");
        }
    }

    @PutMapping("/cancel")
    public ResponseEntity<?> cancelBooking(@RequestBody Booking booking) {
        try {
            bookingService.rejected(booking);
            return ResponseEntity.ok("Booking cancellation processed successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to cancel the booking.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while cancelling the booking.");
        }
    }

    @PostMapping("/cancel-request")
    public ResponseEntity<?> cancelRequest(@RequestBody Booking booking) {
        try {
            notificationService.createCancellationApproval(booking);
            return ResponseEntity.ok("Cancellation request submitted for approval.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to send cancellation request.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred during cancellation request.");
        }
    }
}
