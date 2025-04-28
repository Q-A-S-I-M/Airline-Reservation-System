package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.CustomMappers.BookingRowMapper;
import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Entities.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    JdbcTemplate template;
    @Autowired
    NotificationService notificationService;
    @Override
    public void process(long id) {
        String sql = "UPDATE Payments SET status = 'Successful' WHERE booking_id = ?";
        template.update(sql, id);
        sql = "UPDATE Bookings SET status = 'Waiting for approval' WHERE booking_id = ?";
        template.update(sql, id);
        sql = "Select * From Bookings WHERE id = ?";
        Booking booking = template.queryForObject(sql, new Object[]{id}, new BookingRowMapper());
        notificationService.createBookingApproval(booking);
    }

    @Override
    public Payment get(Long id) {
        return null;
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
