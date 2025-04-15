package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.User;
import com.AirlineManagement.Airline_Management_System.Misc.Login_Request;
import com.AirlineManagement.Airline_Management_System.Misc.SignIn_Request;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {
    public User login(Login_Request request);
    public User update(String username, User updatedUser);
    public void register(SignIn_Request request);
}
