package com.AirlineManagement.Airline_Management_System.CustomMappers;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.AirlineManagement.Airline_Management_System.Entities.AirCraft;
import com.AirlineManagement.Airline_Management_System.Entities.Airline;
import com.AirlineManagement.Airline_Management_System.Entities.Flight;

public class FlightRowMapper implements RowMapper<Flight> {

    @Override
    public Flight mapRow(ResultSet rs, int arg1) throws SQLException {
         Flight flight = new Flight();

        flight.setId(rs.getLong("f.id"));
        flight.setFromLocation(rs.getString("from_location"));
        flight.setToLocation(rs.getString("to_location"));
        flight.setDeparture(rs.getTimestamp("departure"));
        flight.setArrival(rs.getTimestamp("arrival"));
        flight.setBookedSeats(rs.getInt("booked_seats"));
        flight.setTotalSeats(rs.getInt("total_seats"));
        flight.setStatus(rs.getString("f.status"));
        flight.setPrice(rs.getDouble("price"));
        flight.setDuration(rs.getString("duration"));

        Airline airline = new Airline();
        airline.setId(rs.getLong("a.id"));
        airline.setName(rs.getString("name"));

        AirCraft aircraft = new AirCraft();
        aircraft.setId(rs.getLong("ac.id"));
        aircraft.setModel(rs.getString("model"));
        aircraft.setSeats(rs.getInt("seats"));
        aircraft.setStatus(rs.getString("ac.status"));

        flight.setAirline(airline);
        flight.setAircraft(aircraft);

        return flight;
    }
     
}