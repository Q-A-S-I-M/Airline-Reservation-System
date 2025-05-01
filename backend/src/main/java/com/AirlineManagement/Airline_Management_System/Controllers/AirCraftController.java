package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.Entities.AirCraft;
import com.AirlineManagement.Airline_Management_System.Services.AirCraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aircrafts")
public class AirCraftController {

    @Autowired
    private AirCraftService aircraftService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllAircrafts() {
        try {
            List<AirCraft> aircrafts = aircraftService.getAll();
            if (aircrafts.isEmpty()) {
                return ResponseEntity.badRequest().body("No aircrafts found.");
            }
            return ResponseEntity.ok(aircrafts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while fetching aircrafts.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAircraft(@PathVariable Long id) {
        try {
            AirCraft airCraft = aircraftService.get(id);
            if (airCraft == null) {
                return ResponseEntity.badRequest().body("Aircraft not found with ID: " + id);
            }
            return ResponseEntity.ok(airCraft);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while fetching the aircraft.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAircraft(@RequestBody AirCraft aircraft) {
        try {
            AirCraft createdAircraft = aircraftService.create(aircraft);
            return ResponseEntity.ok(createdAircraft);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while creating the aircraft.");
        }
    }
}
