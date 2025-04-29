package com.AirlineManagement.Airline_Management_System.DTOs;

import java.util.List;

import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Entities.Passenger;

public class BookingData {
    private List<Passenger> passengers;
    private Booking booking;
    
    public void setPassengers(List<Passenger> passengers){
        this.passengers = passengers;
    }

    public List<Passenger> getPassengers(){
        return passengers;
    }
    
    public void setBooking(Booking booking){
        this.booking = booking;
    }

    public Booking getBooking(){
        return booking;
    }
}
