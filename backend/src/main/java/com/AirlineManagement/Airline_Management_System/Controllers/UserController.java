package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.Entities.Feedback;
import com.AirlineManagement.Airline_Management_System.Entities.Flight;
import com.AirlineManagement.Airline_Management_System.Entities.User;
import com.AirlineManagement.Airline_Management_System.Misc.FlightFilter;
import com.AirlineManagement.Airline_Management_System.Misc.Login_Request;
import com.AirlineManagement.Airline_Management_System.Misc.SignIn_Request;
import com.AirlineManagement.Airline_Management_System.Repositories.UserRepository;
import com.AirlineManagement.Airline_Management_System.Services.FeedbackService;
import com.AirlineManagement.Airline_Management_System.Services.FlightService;
import com.AirlineManagement.Airline_Management_System.Services.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private FlightService flightService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login_Request request) {
        try{
            User user = userService.login(request);
            return ResponseEntity.ok(user);
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{username}")
    public User updateUser(@PathVariable String username, @RequestBody User updatedUser) {
        return userService.update(username, updatedUser);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignIn_Request request) {
        try{
            userService.register(request);
            return ResponseEntity.ok("User registered successfully");
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/review")
    public ResponseEntity<?> submitReview(@RequestBody Feedback feedback) {
        feedbackService.submit(feedback);
        return ResponseEntity.ok(feedback);
    }
    @PostMapping("/search-flight")
    public ResponseEntity<?> getFlights(@RequestBody FlightFilter filter) {
        try{
            List<Flight> flights = flightService.search(filter);
            return ResponseEntity.ok(flights);
        }catch(RuntimeException e) {
            return ResponseEntity.badRequest().body("No flights available");
        }

    }
    
}
