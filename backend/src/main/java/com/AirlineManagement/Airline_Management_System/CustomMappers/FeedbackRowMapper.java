package com.AirlineManagement.Airline_Management_System.CustomMappers;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.AirlineManagement.Airline_Management_System.Entities.User;
import com.AirlineManagement.Airline_Management_System.Entities.Feedback;
import com.AirlineManagement.Airline_Management_System.Entities.Flight;

public class FeedbackRowMapper implements RowMapper<Feedback> {

    @Override
    public Feedback mapRow(ResultSet rs, int arg1) throws SQLException {
        Feedback fd = new Feedback();

        fd.setId(rs.getLong("f.id"));
        fd.setComments(rs.getString("f.comments"));
        fd.setRating(rs.getInt("f.rating"));
        fd.setTimestamp(rs.getTimestamp("f.timestamp"));

        Flight flight = new Flight();
        flight.setId(rs.getLong("f.flight_id"));

        User user = new User();
        user.setUsername(rs.getString("f.username"));
        
        fd.setFlight(flight);
        fd.setUser(user);

        return fd;
    }
     
}
