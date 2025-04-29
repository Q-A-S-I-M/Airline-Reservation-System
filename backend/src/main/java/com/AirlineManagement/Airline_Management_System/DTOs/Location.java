package com.AirlineManagement.Airline_Management_System.DTOs;

import java.util.List;

public class Location {
    public List<String> source;
    public List<String> destination;
    public List<String> getSource() {
        return source;
    }
    public void setSource(List<String> source) {
        this.source = source;
    }
    public List<String> getDestination() {
        return destination;
    }
    public void setDestination(List<String> destination) {
        this.destination = destination;
    }
}
