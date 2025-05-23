package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Entities.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    JdbcTemplate template;
    // @Autowired
    // NotificationService notificationService;
    @Override
    public void process(long id) {
        // String sql = "UPDATE Payments SET status = 'Successful' WHERE booking_id = ?";
        // template.update(sql, id);
        String sql = "UPDATE Bookings SET status = 'Waiting for approval' WHERE id = ?";
        template.update(sql, id);
        // sql = "Select * From Bookings WHERE id = ?";
        // Booking booking = template.queryForObject(sql, new Object[]{id}, new BookingRowMapper());
        // notificationService.createBookingApproval(booking);
    }

    @Override
    public Payment create(Booking booking) {
        Payment payment = new Payment();
        payment.setStatus("Pending");
        payment.setBooking(booking);
        String sql = "INSERT INTO Payments (status, booking_id) VALUES(?, ?)";
        template.update(sql, payment.getStatus(), payment.getBooking().getId());
        return payment;
    }
}
