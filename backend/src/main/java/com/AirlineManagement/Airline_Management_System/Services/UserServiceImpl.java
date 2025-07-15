package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.User;
import com.AirlineManagement.Airline_Management_System.DTOs.Login_Request;
import com.AirlineManagement.Airline_Management_System.DTOs.SignIn_Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public void register(SignIn_Request request){
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";
        int count = template.queryForObject(sql, Integer.class, request.username);
        if(count == 0){
            String hashedPassword = passwordEncoder.encode(request.password);
        sql = "INSERT INTO Users (username, email, password, role) VALUES (?, ?, ?, ?)";
        template.update(sql, request.username, request.email, hashedPassword, request.role);
        }else{
            throw new RuntimeException("Username already exists!");
        }
    }
}
