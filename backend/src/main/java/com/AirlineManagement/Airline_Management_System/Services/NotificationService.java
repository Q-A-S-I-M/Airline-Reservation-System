package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Entities.Notification;

import java.util.List;

public interface NotificationService {
    public List<Notification> getUserNoti(String username);
    public List<Notification> getAdminNotifications();
    public void createBookingApproval(Booking booking);
    public void createCancellationApproval(Booking booking);
    public void createCancellationAccept(Booking booking);
    public void createCancellationReject(Booking booking);
    public void createBookingAccept(Booking booking);
    public void createBookingReject(Booking booking);
    public void createDeadlineCrossed(Booking booking);
    public void updateNotificationForAdmin(long id);
    public void flightCancelNotification(Booking booking);
}
