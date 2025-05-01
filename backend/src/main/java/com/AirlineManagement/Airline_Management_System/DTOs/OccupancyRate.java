package com.AirlineManagement.Airline_Management_System.DTOs;

public class OccupancyRate {
    private String airline;
    private float occupanyRate;

    public String getAirline(){
        return airline;
    }

    public void setAirline(String airline){
        this.airline = airline;
    }

    public float getRate(){
        return occupanyRate;
    }

    public void setRate(float occupany_rate){
        this.occupanyRate = occupany_rate;
    }
}
