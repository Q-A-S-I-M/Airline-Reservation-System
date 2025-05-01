package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Entities.Notification;
import com.AirlineManagement.Airline_Management_System.Services.BookingService;
import com.AirlineManagement.Airline_Management_System.Services.NotificationService;
import com.AirlineManagement.Airline_Management_System.Services.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    TicketService ticketService;

    @Autowired
    BookingService bookingService;

    @GetMapping("/admin")
    public ResponseEntity<?> getAdminNotifications() {
        try {
            List<Notification> notifications = notificationService.getAdminNotifications();
            return ResponseEntity.ok(notifications);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to retrieve admin notifications.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while fetching admin notifications.");
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserNotifications(@PathVariable String username) {
        try {
            List<Notification> notifications = notificationService.getUserNoti(username);
            return ResponseEntity.ok(notifications);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to retrieve notifications for user: " + username);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while fetching user notifications.");
        }
    }
    @PostMapping("/admin/booking-accept")
    public ResponseEntity<?> bookingAccept(@RequestBody Booking booking) {
        try {
            notificationService.createBookingAccept(booking);
            notificationService.updateNotificationForAdmin(booking.getId());
            ticketService.createTicket(booking);
            return ResponseEntity.ok("Booking accepted and ticket created successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to accept booking or create ticket.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while processing the booking acceptance.");
        }
    }
    @PostMapping("/admin/booking-reject")
    public ResponseEntity<?> bookingReject(@RequestBody Booking booking) {
        try {
            notificationService.createBookingReject(booking);
            notificationService.updateNotificationForAdmin(booking.getId());
            bookingService.rejected(booking);
            return ResponseEntity.ok("Booking rejected successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to reject the booking.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while rejecting the booking.");
        }
    }
    @PostMapping("/admin/cancellation-accept")
    public ResponseEntity<?> cancellationAccept(@RequestBody Booking booking) {
        try {
            notificationService.createCancellationAccept(booking);
            notificationService.updateNotificationForAdmin(booking.getId());
            bookingService.cancelTickets(booking);
            return ResponseEntity.ok("Cancellation accepted and tickets cancelled successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to accept cancellation or cancel tickets.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while processing the cancellation acceptance.");
        }
    }
    @PostMapping("/admin/cancellation-reject")
    public ResponseEntity<?> cancellationReject(@RequestBody Booking booking) {
        try {
            notificationService.createCancellationReject(booking);
            notificationService.updateNotificationForAdmin(booking.getId());
            return ResponseEntity.ok("Cancellation rejected successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to reject the cancellation.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while rejecting the cancellation.");
        }
    }
}
