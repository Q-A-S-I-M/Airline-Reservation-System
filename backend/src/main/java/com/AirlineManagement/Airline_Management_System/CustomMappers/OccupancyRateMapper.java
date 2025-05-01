package com.AirlineManagement.Airline_Management_System.CustomMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.AirlineManagement.Airline_Management_System.DTOs.OccupancyRate;

public class OccupancyRateMapper implements RowMapper<OccupancyRate>{

    @Override
    @Nullable
    public OccupancyRate mapRow(ResultSet rs, int arg1) throws SQLException {
        OccupancyRate rates = new OccupancyRate();

        rates.setAirline(rs.getString("airline"));
        rates.setRate(rs.getFloat("occupancyRate"));

        return rates;
    }
    
}
