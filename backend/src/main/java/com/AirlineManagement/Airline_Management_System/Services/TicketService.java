package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Entities.Ticket;

import java.util.List;

public interface TicketService {
    public List<Ticket> getTickets(String username);
    public void createTicket(Booking booking);
}
