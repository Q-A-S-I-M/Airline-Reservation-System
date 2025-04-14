package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.Entities.Feedback;
import com.AirlineManagement.Airline_Management_System.Repositories.FeedbackRepository;
import com.AirlineManagement.Airline_Management_System.Services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public Feedback submitFeedback(@RequestBody Feedback feedback) {
        return feedbackService.submit(feedback);
    }

    @GetMapping("/flight/{flightId}")
    public List<Feedback> getFeedbackByFlight(@PathVariable Long flightId) {
        return feedbackService.get(flightId);
    }
}
