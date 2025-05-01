package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.Entities.Flight;
import com.AirlineManagement.Airline_Management_System.Services.FlightService;
import com.AirlineManagement.Airline_Management_System.DTOs.FlightCreation;
import com.AirlineManagement.Airline_Management_System.DTOs.FlightFilter;
import com.AirlineManagement.Airline_Management_System.DTOs.Location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/get-flights")
    public ResponseEntity<?> getflights() {
        try {
            List<Flight> flights = flightService.getAllFlights();
            return ResponseEntity.ok(flights);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to retrieve flights.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while fetching flights.");
        }
    }

    @PutMapping("/update-flight/{id}")
    public ResponseEntity<String> updateFlights(@PathVariable Long id, @RequestBody String status) {
        try {
            flightService.updateStatus(id, status);
            return ResponseEntity.ok("Flight status updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Invalid flight ID or status.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while updating flight status.");
        }
    }

    @GetMapping("/load-flightCreation")
    public ResponseEntity<?> load() {
        try {
            FlightCreation page = flightService.getData();
            if (page.aircrafts.isEmpty()) {
                return ResponseEntity.badRequest().body("No aircrafts available at the moment.");
            } else {
                return ResponseEntity.ok(page);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to load flight creation data.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while loading data.");
        }
    }

    @PostMapping("/create-flight")
    public ResponseEntity<String> createFlight(@RequestBody Flight flight) {
        try {
            flightService.create(flight);
            return ResponseEntity.ok("Flight created successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to create flight. Please check the input data.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while creating the flight.");
        }
    }

    @GetMapping("/flight-locations")
    public ResponseEntity<?> getLocations() {
        try {
            Location locations = flightService.getlocations();
            return ResponseEntity.ok(locations);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to load flight locations.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while retrieving locations.");
        }
    }

    @PostMapping("/search-flight")
    public ResponseEntity<?> getFilteredFlights(@RequestBody FlightFilter filter) {
        try {
            List<Flight> flights = flightService.search(filter);
            return ResponseEntity.ok(flights);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("No matching flights found.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while searching for flights.");
        }
    }
}
