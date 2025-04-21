package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.Entities.Flight;
import com.AirlineManagement.Airline_Management_System.Repositories.FlightRepository;
import com.AirlineManagement.Airline_Management_System.Services.FlightService;
import com.AirlineManagement.Airline_Management_System.Misc.FlightFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flight")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @GetMapping("/{id}")
    public Flight getFlight(@PathVariable Long id) {
        return flightService.get(id);
    }
    @GetMapping("/filter")
    public List<Flight> getFlights(@RequestBody FlightFilter filter) {
        return flightService.search(filter);
    }

    @PostMapping
    public void createFlight(@RequestBody Flight flight) {
        flightService.create(flight);
    }

    @PutMapping("/{id}")
    public Flight updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        return flightService.update(id, flight);
    }
}
