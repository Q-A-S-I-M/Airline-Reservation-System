package com.AirlineManagement.Airline_Management_System.CustomMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.AirlineManagement.Airline_Management_System.Entities.Notification;
import com.AirlineManagement.Airline_Management_System.Entities.Booking;

public class NotificationRowMapper implements RowMapper<Notification>{

    @Override
    @Nullable
    public Notification mapRow(ResultSet rs, int arg1) throws SQLException {
        Notification notification = new Notification();

        notification.setId(rs.getLong("id"));
        notification.setStatus(rs.getString("status"));
        notification.setType(rs.getString("type"));
        notification.setTimestamp(rs.getTimestamp("timestamp"));
        notification.setDescription(rs.getString("description"));
        notification.setforAdmin(rs.getString("for_admin"));

        Booking booking = new Booking();
        booking.setId(rs.getLong("booking_id"));

        notification.setBooking(booking);

        return notification;
    }
    
}
