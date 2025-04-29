package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Entities.Payment;

public interface PaymentService {
    public void process(long id);
    public Payment create(Booking booking);
}
