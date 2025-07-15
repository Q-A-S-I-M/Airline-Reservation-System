package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.User;
import com.AirlineManagement.Airline_Management_System.DTOs.Login_Request;
import com.AirlineManagement.Airline_Management_System.DTOs.SignIn_Request;

public interface UserService{
    public void register(SignIn_Request request);
}
