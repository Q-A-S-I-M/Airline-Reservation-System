package com.AirlineManagement.Airline_Management_System.CustomMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.AirlineManagement.Airline_Management_System.Entities.RefreshToken;
import com.AirlineManagement.Airline_Management_System.Entities.User;

public class RefreshTokenRowMapper implements RowMapper<RefreshToken> {
    @Override
    public RefreshToken mapRow(ResultSet rs, int rowNum) throws SQLException {
        RefreshToken token = new RefreshToken();
        token.setToken(rs.getString("token"));
        token.setUserAgent(rs.getString("user_agent"));
        token.setIpAddress(rs.getString("ip_address"));
        // manually convert LocalDateTime to Instant
        token.setExpiryDate(rs.getTimestamp("expiry_date").toInstant());

        // set user using just the username or query full user separately
        User user = new User();
        user.setUsername(rs.getString("user_id")); // adjust if needed
        token.setUser(user);

        return token;
    }
}

