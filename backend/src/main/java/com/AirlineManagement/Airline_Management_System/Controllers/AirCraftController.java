package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.Entities.AirCraft;
import com.AirlineManagement.Airline_Management_System.Services.AirCraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/aircrafts")
public class AirCraftController {
    @Autowired
    private AirCraftService aircraftService;

    @GetMapping("/get-all")
    public List<AirCraft> getAllAircrafts() {
        return aircraftService.getAll();
    }

    @GetMapping("/{id}")
    public AirCraft getAircraft(@PathVariable Long id) {
        return aircraftService.get(id);
    }

    @PostMapping("/create")
    public AirCraft createAircraft(@RequestBody AirCraft aircraft) {
        return aircraftService.create(aircraft);
    }
}

