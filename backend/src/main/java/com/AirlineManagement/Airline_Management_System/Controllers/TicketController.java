package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.Entities.Ticket;
import com.AirlineManagement.Airline_Management_System.Services.TicketService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getTickets(@PathVariable String username) {
        try {
            List<Ticket> tickets = ticketService.getTickets(username);
            if (tickets.isEmpty()) {
                return ResponseEntity.badRequest().body("No tickets found for user: " + username);
            }
            return ResponseEntity.ok(tickets);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to retrieve tickets for user: " + username);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while fetching tickets.");
        }
    }
}
