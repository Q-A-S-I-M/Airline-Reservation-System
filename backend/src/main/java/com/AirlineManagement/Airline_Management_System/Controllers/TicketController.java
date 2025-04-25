package com.AirlineManagement.Airline_Management_System.Controllers;


import com.AirlineManagement.Airline_Management_System.Entities.Ticket;
import com.AirlineManagement.Airline_Management_System.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/{id}")
    public Ticket getTicket(@PathVariable Long id) {
        return ticketService.get(id);
    }

    @GetMapping("/flight/{flightId}/passenger/{passengerId}")
    public Ticket getTicketByFlightAndPassenger(@PathVariable Long flightId, @PathVariable Long passengerId) {
        return ticketService.getSpecific(flightId, passengerId);
    }
}