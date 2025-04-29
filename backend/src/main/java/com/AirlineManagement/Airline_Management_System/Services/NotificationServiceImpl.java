package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Entities.Notification;
import com.AirlineManagement.Airline_Management_System.CustomMappers.NotificationRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    JdbcTemplate template;
    @Override
    public List<Notification> getUserNoti(String username) {
        String sql = "Select id From Bookings where username = ?";
        List<Integer> bookingIds = template.queryForList(sql, new Object[]{username}, Integer.class);
        List<Notification> notifications = new ArrayList<>();
        sql = "Select * From Notifications WHERE for_admin = 'No' AND status = 'Untreated' AND booking_id = ?";
        for (int i = 0; i < bookingIds.size(); i++) {
            notifications.addAll(template.query(sql, new Object[]{bookingIds.get(i)}, new BeanPropertyRowMapper<>(Notification.class)));
        }
        sql = "UPDATE Notifications SET status = 'Treated' WHERE id = ?";
        for (int i = 0; i < notifications.size(); i++) {
            template.update(sql, notifications.get(i).getId());
        }
        return notifications;
    }

    @Override
    public void createBookingApproval(Booking booking) {
        Notification notification = new Notification();
        notification.setType("Blue");
        notification.setBooking(booking);
        notification.setforAdmin("Yes");
        notification.setStatus("Untreated");
        notification.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        String desc = booking.getUser().getUsername()+" has completed the payment for Booking ID: "+booking.getId()+".";
        notification.setDescription(desc);
        insert(notification);
    }

    void insert(Notification notification){
        String sql = "INSERT INTO Notifications (type, booking_id, for_admin, status, timestamp, description) VALUES(?, ?, ?, ?, ?, ?)";
        template.update(sql, notification.getType(), notification.getBooking().getId(), notification.getforAdmin(), notification.getStatus(),(java.sql.Timestamp) notification.getTimestamp(), notification.getDescription());
    }

    @Override
    public void createCancellationApproval(Booking booking) {
        Notification notification = new Notification();
        notification.setType("Red");
        notification.setBooking(booking);
        notification.setforAdmin("Yes");
        notification.setStatus("Untreated");
        notification.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        String desc = booking.getUser().getUsername()+" has requested cancellation for Booking ID: "+booking.getId()+".";
        notification.setDescription(desc);
        insert(notification);
    }

    @Override
    public void createCancellationAccept(Booking booking) {
        Notification notification = new Notification();
        notification.setType("Blue");
        notification.setBooking(booking);
        notification.setforAdmin("No");
        notification.setStatus("Untreated");
        notification.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        String desc = "Your cancellation request for Booking ID: "+booking.getId()+" has been accepted. Your payment is refunded.";
        notification.setDescription(desc);
        insert(notification);
    }

    @Override
    public void createCancellationReject(Booking booking) {
        Notification notification = new Notification();
        notification.setType("Red");
        notification.setBooking(booking);
        notification.setforAdmin("No");
        notification.setStatus("Untreated");
        notification.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        String desc = "Your cancellation request for Booking ID: "+booking.getId()+" has been rejected.";
        notification.setDescription(desc);
        insert(notification);
    }

    @Override
    public void createBookingAccept(Booking booking) {
        Notification notification = new Notification();
        notification.setType("Blue");
        notification.setBooking(booking);
        notification.setforAdmin("No");
        notification.setStatus("Untreated");
        notification.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        String desc = "Your booking request for Booking ID: "+booking.getId()+" has been accepted.";
        notification.setDescription(desc);
        insert(notification);
    }

    @Override
    public void createBookingReject(Booking booking) {
        Notification notification = new Notification();
        notification.setType("Red");
        notification.setBooking(booking);
        notification.setforAdmin("No");
        notification.setStatus("Untreated");
        notification.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        String desc = "Your booking request for Booking ID: "+booking.getId()+" has been rejected. Your payment is refunded.";
        notification.setDescription(desc);
        insert(notification);
    }

    @Override
    public void createDeadlineCrossed(Booking booking) {
        Notification notification = new Notification();
        notification.setType("Red");
        notification.setBooking(booking);
        notification.setforAdmin("No");
        notification.setStatus("Untreated");
        notification.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        String desc = "The payment deadline for Booking ID: "+booking.getId()+" has passed. Please create a new booking request.";
        notification.setDescription(desc);
        insert(notification);
    }

    @Override
    public List<Notification> getAdminNotifications() {
        String sql = "Select * From Notifications Where for_admin = 'Yes' AND status = 'Untreated'";
        List<Notification> notifications = template.query(sql, new NotificationRowMapper());
        return notifications;
    }

    @Override
    public void updateNotificationForAdmin(long id) {
        String sql = "UPDATE Notifications SET status = 'Treated' WHERE for_admin = 'Yes' AND booking_id = ?";
        template.update(sql, id);
    }

    @Override
    public void flightCancelNotification(Booking booking) {
        Notification notification = new Notification();
        notification.setType("Red");
        notification.setBooking(booking);
        notification.setforAdmin("No");
        notification.setStatus("Untreated");
        notification.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        String desc = "Your flight with ID: "+booking.getFlight().getId()+" is cancelled due to some problems. Your payments are refunded.";
        notification.setDescription(desc);
        insert(notification);
    }
}
