package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Entities.Notification;
import com.AirlineManagement.Airline_Management_System.CustomMappers.NotificationRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    JdbcTemplate template;
    @Override
    public List<Notification> get(Long userId) {
        return null;
    }

    @Override
    public void createBookingApproval(Booking booking) {
        Notification notification = new Notification();
        notification.setType("Blue");
        notification.setBooking(booking);
        notification.setforAdmin("Yes");
        notification.setStatus("Untreated");
        LocalDateTime localDateTime = LocalDateTime.now();
        Date now = (Date) Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        notification.setTimestamp(now);
        String desc = booking.getUser().getUsername()+" has completed the payment for Booking ID: "+booking.getId()+".";
        notification.setDescription(desc);
        insert(notification);
    }

    void insert(Notification notification){
        String sql = "INSERT INTO Notifications (type, booking_id, for_admin, status, timestamp, description) VALUES(?, ?, ?, ?, ?, ?)";
        template.update(sql, notification.getType(), notification.getBooking().getId(), notification.getforAdmin(), notification.getStatus(), notification.getTimestamp(), notification.getDescription());
    }

    @Override
    public void createCancellationApproval(Booking booking) {
        Notification notification = new Notification();
        notification.setType("Red");
        notification.setBooking(booking);
        notification.setforAdmin("Yes");
        notification.setStatus("Untreated");
        LocalDateTime localDateTime = LocalDateTime.now();
        Date now = (Date) Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        notification.setTimestamp(now);
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
        LocalDateTime localDateTime = LocalDateTime.now();
        Date now = (Date) Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        notification.setTimestamp(now);
        String desc = "Your cancellation request for Booking ID: "+booking.getId()+" has been accepted.";
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
        LocalDateTime localDateTime = LocalDateTime.now();
        Date now = (Date) Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        notification.setTimestamp(now);
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
        LocalDateTime localDateTime = LocalDateTime.now();
        Date now = (Date) Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        notification.setTimestamp(now);
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
        LocalDateTime localDateTime = LocalDateTime.now();
        Date now = (Date) Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        notification.setTimestamp(now);
        String desc = "Your booking request for Booking ID: "+booking.getId()+" has been rejected.";
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
        LocalDateTime localDateTime = LocalDateTime.now();
        Date now = (Date) Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        notification.setTimestamp(now);
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
}
