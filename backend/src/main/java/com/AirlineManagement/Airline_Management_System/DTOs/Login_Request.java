package com.AirlineManagement.Airline_Management_System.DTOs;

public class Login_Request {
    public String username;
    public String password;
    public Login_Request(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
