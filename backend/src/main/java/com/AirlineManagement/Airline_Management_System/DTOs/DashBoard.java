package com.AirlineManagement.Airline_Management_System.DTOs;

import java.util.List;

public class DashBoard {
    private int passengers;
    private int flights;
    private int revenue;
    private List<OccupancyRate> occupany_rate;
    private List<FlightStatus> status_summary;

    public int getPassengers() {
        return passengers;
    }

    public int getFlights() {
        return flights;
    }

    public int getRevenue() {
        return revenue;
    }

    public List<OccupancyRate> getOccupany_rate() {
        return occupany_rate;
    }

    public List<FlightStatus> getStatus_summary() {
        return status_summary;
    }
    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public void setFlights(int flights) {
        this.flights = flights;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public void setOccupany_rate(List<OccupancyRate> occupany_rate) {
        this.occupany_rate = occupany_rate;
    }

    public void setStatus_summary(List<FlightStatus> status_summary) {
        this.status_summary = status_summary;
    }
    
}
