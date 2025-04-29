package com.AirlineManagement.Airline_Management_System.Controllers;


import com.AirlineManagement.Airline_Management_System.Entities.Ticket;
import com.AirlineManagement.Airline_Management_System.Services.TicketService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin("*")
@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/user/{username}")
    public List<Ticket> getTickets(@PathVariable String username){
        return ticketService.getTickets(username);
    }
}