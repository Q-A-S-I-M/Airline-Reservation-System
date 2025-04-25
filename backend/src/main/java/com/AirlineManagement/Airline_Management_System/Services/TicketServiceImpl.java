package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.Ticket;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService{
    @Override
    public Ticket get(Long id) {
        return null;
    }

    @Override
    public Ticket getSpecific(Long flightId, Long passengerId) {
        return null;
    }
}
