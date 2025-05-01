package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.Entities.Airline;
import com.AirlineManagement.Airline_Management_System.Services.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airlines")
public class AirlineController {

    @Autowired
    private AirlineService airlineService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllAirlines() {
        try {
            List<Airline> airlines = airlineService.getAll();
            if (airlines.isEmpty()) {
                return ResponseEntity.badRequest().body("No airlines found.");
            }
            return ResponseEntity.ok(airlines);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while fetching airlines.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAirline(@PathVariable Long id) {
        try {
            Airline airline = airlineService.get(id);
            if (airline == null) {
                return ResponseEntity.badRequest().body("Airline not found with ID: " + id);
            }
            return ResponseEntity.ok(airline);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while fetching the airline.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAirline(@RequestBody Airline airline) {
        try {
            Airline createdAirline = airlineService.create(airline);
            return ResponseEntity.ok(createdAirline);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while creating the airline.");
        }
    }
}
