package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.Entities.Airline;
import com.AirlineManagement.Airline_Management_System.Services.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/airlines")
public class AirlineController {
    @Autowired
    private AirlineService airlineService;

    @GetMapping("/get-all")
    public List<Airline> getAllAirlines() {
        return airlineService.getAll();
    }

    @GetMapping("/{id}")
    public Airline getAirline(@PathVariable Long id) {
        return airlineService.get(id);
    }

    @PostMapping("/create")
    public Airline createAirline(@RequestBody Airline airline) {
        return airlineService.create(airline);
    }
}
