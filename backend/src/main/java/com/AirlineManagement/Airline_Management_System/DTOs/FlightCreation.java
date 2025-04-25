package com.AirlineManagement.Airline_Management_System.DTOs;

import java.util.List;

import com.AirlineManagement.Airline_Management_System.Entities.AirCraft;
import com.AirlineManagement.Airline_Management_System.Entities.Airline;

public class FlightCreation {
    public List<Airline> airlines;
    public List<AirCraft> aircrafts;
    public FlightCreation(List<Airline> airlines, List<AirCraft> airCrafts){
        this.airlines = airlines;
        this.aircrafts = airCrafts;
    }
}
