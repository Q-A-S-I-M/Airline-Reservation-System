package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Entities.Notification;
import com.AirlineManagement.Airline_Management_System.Services.BookingService;
import com.AirlineManagement.Airline_Management_System.Services.NotificationService;
import com.AirlineManagement.Airline_Management_System.Services.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Notification> getAdminNotifications(){
        return notificationService.getAdminNotifications();
    }
    @GetMapping("/user/{username}")
    public List<Notification> getUserNotifications(@PathVariable String username){
        return notificationService.getUserNoti(username);
    }
    @PostMapping("/admin/booking-accept")
    public void bookingAccept(@RequestBody Booking booking){
        notificationService.createBookingAccept(booking);
        notificationService.updateNotificationForAdmin(booking.getId());
        ticketService.createTicket(booking);
    }
    @PostMapping("/admin/booking-reject")
    public void bookingReject(@RequestBody Booking booking){
        notificationService.createBookingReject(booking);
        notificationService.updateNotificationForAdmin(booking.getId());
        bookingService.rejected(booking);
    }
    @PostMapping("/admin/cancellation-accept")
    public void cancellationAccept(@RequestBody Booking booking){
        notificationService.createCancellationAccept(booking);
        notificationService.updateNotificationForAdmin(booking.getId());
        bookingService.cancelTickets(booking);
    }
    @PostMapping("/admin/cancellation-reject")
    public void cancellationReject(@RequestBody Booking booking){
        notificationService.createCancellationReject(booking);
        notificationService.updateNotificationForAdmin(booking.getId());
    }
}
