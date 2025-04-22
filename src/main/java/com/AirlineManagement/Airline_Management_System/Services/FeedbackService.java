package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.Feedback;
import com.AirlineManagement.Airline_Management_System.Entities.Flight;


import java.util.List;

public interface FeedbackService {
    public Feedback submit(Feedback feedback);
    public List<Flight> get();
    public List<Feedback> getFeedbacks(Long id);
}
