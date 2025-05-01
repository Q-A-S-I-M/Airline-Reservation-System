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
    @Override
    public User login(Login_Request request) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try{
            User user = template.queryForObject(sql, new Object[]{request.username}, new BeanPropertyRowMapper<>(User.class));
            if(passwordEncoder.matches(request.password, user.getPassword())){
                return user;
            }else{
                throw new RuntimeException("Invalid Password!");
            }
        }catch(EmptyResultDataAccessException e){
            throw new RuntimeException("User not registered!");
        }
    }
    public void register(SignIn_Request request){
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";
        int count = template.queryForObject(sql, Integer.class, request.username);
        if(count == 0){
            String hashedPassword = passwordEncoder.encode(request.password);
        sql = "INSERT INTO Users (username, first_name, last_name, email, password, dob) VALUES (?, ?, ?, ?, ?, ?)";
        template.update(sql, request.username, request.firstName, request.lastName, request.email, hashedPassword, request.dob);
        }else{
            throw new RuntimeException("Username already exists!");
        }
    }
}
