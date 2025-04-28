package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.Entities.AirCraft;
import com.AirlineManagement.Airline_Management_System.Repositories.AirCraftRepository;
import com.AirlineManagement.Airline_Management_System.Services.AirCraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aircrafts")
public class AirCraftController {
    @Autowired
    private AirCraftService aircraftService;

    @GetMapping
    public List<AirCraft> getAllAircrafts() {
        return aircraftService.getAll();
    }

    @GetMapping("/{id}")
    public AirCraft getAircraft(@PathVariable Long id) {
        return aircraftService.get(id);
    }

    @PostMapping
    public AirCraft createAircraft(@RequestBody AirCraft aircraft) {
        return aircraftService.create(aircraft);
    }

    @DeleteMapping("/{id}")
    public void deleteAircraft(@PathVariable Long id) {
        aircraftService.delete(id);
    }
}

