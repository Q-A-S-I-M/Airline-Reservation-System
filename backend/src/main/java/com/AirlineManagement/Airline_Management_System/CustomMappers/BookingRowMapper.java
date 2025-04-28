package com.AirlineManagement.Airline_Management_System.CustomMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Entities.Feedback;
import com.AirlineManagement.Airline_Management_System.Entities.Flight;
import com.AirlineManagement.Airline_Management_System.Entities.User;

public class BookingRowMapper implements RowMapper<Booking>{

    @Override
    @Nullable
    public Booking mapRow(ResultSet rs, int arg1) throws SQLException {
        Booking booking = new Booking();

        booking.setId(rs.getLong("id"));
        booking.setStatus(rs.getString("status"));
        booking.setAmount(rs.getInt("amount"));
        booking.setTimestamp(rs.getTimestamp("timestamp"));
        booking.setPaymentDeadline(rs.getTimestamp("payment_deadline"));

        Flight flight = new Flight();
        flight.setId(rs.getLong("flight_id"));

        User user = new User();
        user.setUsername(rs.getString("username"));
        
        booking.setFlight(flight);
        booking.setUser(user);

        return booking;
    }
    
}
