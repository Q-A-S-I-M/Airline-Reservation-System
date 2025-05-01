package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.DTOs.DashBoard;
import com.AirlineManagement.Airline_Management_System.DTOs.Login_Request;
import com.AirlineManagement.Airline_Management_System.Services.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody Login_Request request) {
        try {
            if (request.password.equals("kmc31y8") && request.username.equals("batman")) {
                System.out.println("Admin logged in");
                return ResponseEntity.ok().build();
            } else {
                throw new RuntimeException(); // No custom message needed
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Login failed: Invalid username or password.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred during login.");
        }
    }

    @GetMapping("/dashboard")
    ResponseEntity<?> dashboard() {
        try {
            DashBoard dashboard = adminService.getDashboard();
            return ResponseEntity.ok(dashboard);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to load dashboard data.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while fetching the dashboard.");
        }
    }
}
