package com.AirlineManagement.Airline_Management_System.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.AirlineManagement.Airline_Management_System.DTOs.DashBoard;
import com.AirlineManagement.Airline_Management_System.DTOs.FlightStatus;
import com.AirlineManagement.Airline_Management_System.DTOs.OccupancyRate;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    JdbcTemplate template;

    @Override
    public DashBoard getDashboard() {
        DashBoard dashBoard = new DashBoard();
        dashBoard.setPassengers(template.queryForObject("Select COUNT(*) From Passengers",Integer.class));
        dashBoard.setFlights(template.queryForObject("Select COUNT(*) From Flights",Integer.class));
        dashBoard.setRevenue(template.queryForObject("Select SUM(amount) From Bookings where status = 'Approved'",Integer.class));
        dashBoard.setOccupany_rate(getOccupancyRatePerAirline());
        dashBoard.setStatus_summary(getFlightStatusSummary());

        return dashBoard;
    }
    private List<OccupancyRate> getOccupancyRatePerAirline() {
        String sql = "SELECT a.name AS airline, ROUND(AVG(f.booked_seats * 100.0 / f.total_seats)) AS occupancyRate FROM airlines a LEFT JOIN flights f ON f.airline_id = a.id WHERE f.status NOT IN ('Scheduled', 'Delayed', 'Cancelled') GROUP BY a.name";
        
        return template.query(sql, new BeanPropertyRowMapper<>(OccupancyRate.class));
    }
    private List<FlightStatus> getFlightStatusSummary() {
        String sql = "SELECT status, COUNT(*) AS count FROM flights GROUP BY status";

        return template.query(sql, new BeanPropertyRowMapper<>(FlightStatus.class));
    }
}
