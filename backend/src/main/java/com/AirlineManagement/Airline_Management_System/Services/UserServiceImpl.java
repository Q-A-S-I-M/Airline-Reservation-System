package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.AirCraft;
import com.AirlineManagement.Airline_Management_System.Entities.User;
import com.AirlineManagement.Airline_Management_System.DTOs.Login_Request;
import com.AirlineManagement.Airline_Management_System.DTOs.SignIn_Request;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private JdbcTemplate template;
    @Override
    public User login(Login_Request request) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try{
            User user = template.queryForObject(sql, new Object[]{request.username}, new BeanPropertyRowMapper<>(User.class));
            if(user.getPassword().equals(request.password)){
                return user;
            }else{
                throw new RuntimeException("Invalid Password!");
            }
        }catch(EmptyResultDataAccessException e){
            throw new RuntimeException("User not registered!");
        }
    }

    @Override
    public User update(String username, User updatedUser) {
        String sql = "UPDATE Users SET username = ?, contact = ?, dob = ?, email = ?, first_name = ?, last_name = ?, password = ? WHERE username = ?";
        template.update(sql, updatedUser.getUsername(), updatedUser.getContact(), updatedUser.getDob(), updatedUser.getEmail(), updatedUser.getFirstName(), updatedUser.getLastName(), updatedUser.getPassword(), username);
        return updatedUser;
    }
    public void register(SignIn_Request request){
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";
        int count = template.queryForObject(sql, Integer.class, request.username);
        if(count == 0){
            
        sql = "INSERT INTO Users (username, first_name, last_name, contact, email, password, dob) VALUES (?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, request.username, request.firstName, request.lastName, request.contact, request.email, request.password, request.dob);
        }else{
            throw new RuntimeException("Username already exists!");
        }
    }
}
