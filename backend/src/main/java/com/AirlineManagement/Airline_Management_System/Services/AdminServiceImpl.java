package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.CustomMappers.OccupancyRateMapper;
import com.AirlineManagement.Airline_Management_System.DTOs.DashBoard;
import com.AirlineManagement.Airline_Management_System.DTOs.FlightStatus;
import com.AirlineManagement.Airline_Management_System.DTOs.OccupancyRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    JdbcTemplate template;

    @Override
    public DashBoard getDashboard() {
        DashBoard dashBoard = new DashBoard();

        dashBoard.setPassengers(getPassengers());
        dashBoard.setFlights(getFlights());
        dashBoard.setRevenue(getRevenue());
        dashBoard.setOccupany_rate(getOccupancyRatePerAirline());
        dashBoard.setStatus_summary(getFlightStatusSummary());

        return dashBoard;
    }

    private int getPassengers() {
        String sql = "SELECT COUNT(id) FROM Passengers";
        return template.queryForObject(sql, Integer.class);
    }

    private int getFlights() {
        String sql = "SELECT COUNT(id) FROM Flights";
        return template.queryForObject(sql, Integer.class);
    }

    private int getRevenue() {
        String sql = "SELECT COALESCE(SUM(amount), 0) FROM Bookings Where status = 'Approved'";
        return template.queryForObject(sql, Integer.class);
    }

    private List<OccupancyRate> getOccupancyRatePerAirline() {
        String sql = "SELECT a.name AS airline, ROUND(AVG(f.booked_seats * 100.0 / f.total_seats), 2) AS occupancyRate FROM flights f JOIN airlines a ON f.airline_id = a.id GROUP BY a.name";

        return template.query(sql, new OccupancyRateMapper());
    }

    private List<FlightStatus> getFlightStatusSummary() {
        String sql = "SELECT status, COUNT(*) AS count FROM flights GROUP BY status";

        return template.query(sql, new BeanPropertyRowMapper<>(FlightStatus.class));
    }
}
