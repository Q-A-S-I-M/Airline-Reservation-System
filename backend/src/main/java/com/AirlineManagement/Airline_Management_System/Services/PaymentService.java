package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.Booking;
import com.AirlineManagement.Airline_Management_System.Entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface PaymentService {
    public void process(long id);
    public Payment create(Booking booking);
    public Payment get(Long id);
}
