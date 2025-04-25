package com.AirlineManagement.Airline_Management_System.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.AirlineManagement.Airline_Management_System.Entities.Flight;
import com.AirlineManagement.Airline_Management_System.CustomMappers.FlightRowMapper;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    JdbcTemplate template;
}
