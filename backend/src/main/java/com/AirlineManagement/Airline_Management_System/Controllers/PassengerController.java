package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.Entities.Passenger;
import com.AirlineManagement.Airline_Management_System.Repositories.PassengerRepository;
import com.AirlineManagement.Airline_Management_System.Services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passengers")
public class PassengerController {
    @Autowired
    private PassengerService passengerService;

    @PostMapping
    public Passenger createPassenger(@RequestBody Passenger passenger) {
        return passengerService.create(passenger);
    }

    @GetMapping("/{id}")
    public Passenger getPassenger(@PathVariable Long id) {
        return passengerService.get(id);
    }

    @PutMapping("/{id}")
    public Passenger updatePassenger(@PathVariable Long id, @RequestBody Passenger updated) {
        return passengerService.update(id, updated);
    }
}
