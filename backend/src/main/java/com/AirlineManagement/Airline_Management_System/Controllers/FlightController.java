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
    @GetMapping("/flight-locations")
    public Location getLocations(){
        return flightService.getlocations();
    }
    @PostMapping("/search-flight")
    public ResponseEntity<?> getFilteredFlights(@RequestBody FlightFilter filter) {
        try{
            List<Flight> flights = flightService.search(filter);
            return ResponseEntity.ok(flights);
        }catch(RuntimeException e) {
            return ResponseEntity.badRequest().body("No flights available");
        }

    }
}
