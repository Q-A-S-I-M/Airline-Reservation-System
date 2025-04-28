package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Entities.Ticket;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface TicketService {
    public Ticket get(Long id);
    public Ticket getSpecific(Long flightId, Long passengerId);
    public void createTicket(Booking booking);
    public void rejected(long id);
    public void cancelTicket(Ticket ticket);
}
