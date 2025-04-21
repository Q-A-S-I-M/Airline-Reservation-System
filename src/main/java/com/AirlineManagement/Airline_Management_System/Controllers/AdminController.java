package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.DTOs.FlightCreation;
import com.AirlineManagement.Airline_Management_System.Entities.AirCraft;
import com.AirlineManagement.Airline_Management_System.Entities.Flight;
import com.AirlineManagement.Airline_Management_System.Entities.Notification;
import com.AirlineManagement.Airline_Management_System.Misc.Login_Request;
import com.AirlineManagement.Airline_Management_System.Misc.Update_Request;
import com.AirlineManagement.Airline_Management_System.Services.AdminService;
import com.AirlineManagement.Airline_Management_System.Services.FlightService;
import com.AirlineManagement.Airline_Management_System.Services.FlightServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.management.RuntimeErrorException;


@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService service;
    @Autowired
    private FlightService flightService;
    @GetMapping("/login")
    ResponseEntity<?> login(@RequestBody Login_Request request){
        try{
            if(request.password.equals("kmc31y8")){
                System.out.println("Admin logged in");
                return ResponseEntity.ok().build();
            }else{
                throw new RuntimeException("Invalid password!");
            }
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/flights")
    public List<Flight> getflights() {
        return flightService.getAllFlights();
    }
    @PutMapping("update-flight/{id}")
    public ResponseEntity<String> updateFlights(@PathVariable Long id, @RequestBody String status) {
        try {
        flightService.updateStatus(id, status);
        return ResponseEntity.ok("Flight status updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update flight status.");
        }
    }
    @GetMapping("/load-flightCreation")
    public ResponseEntity<?> load() {
        FlightCreation page = flightService.getData();
        if(page.aircrafts.isEmpty()){
            return ResponseEntity.badRequest().body("No aircrafts available at the moment");
        }else{
            return ResponseEntity.ok(page);
        }
    }
    @PostMapping("/create-flight")
    public ResponseEntity<String> createFlight(@RequestBody Flight flight) {
        flightService.create(flight);
        return ResponseEntity.ok("Flight created successfully.");
    }
    @GetMapping("/requests")
    List<Notification> get_noti(){return null;}
    @GetMapping("/get-airCrafts")
    List<AirCraft> get_airCrafts(){return null;}
    @PutMapping("/assign-airCraft")
    void assign_aircraft(@RequestBody AirCraft airCraft){}
    @GetMapping("/generate-reports")
    void generate_reports(){}
    @PutMapping("/confirm-booking")
    void confirm_booking(){}
    @PutMapping("/cancel-booking")
    void cancel_booking(){}
}
