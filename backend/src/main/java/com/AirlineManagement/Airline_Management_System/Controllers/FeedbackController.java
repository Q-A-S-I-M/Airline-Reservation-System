package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.Entities.Feedback;
import com.AirlineManagement.Airline_Management_System.Services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/submit")
    public Feedback submitFeedback(@RequestBody Feedback feedback) {
        return feedbackService.submit(feedback);
    }
    @GetMapping("/get-feedbacks")
    public ResponseEntity<?> getFeedbacks() {
        return ResponseEntity.ok(feedbackService.get());
    }
    @GetMapping("/get-feedbacks/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getFeedbacks(id));
    }
}
