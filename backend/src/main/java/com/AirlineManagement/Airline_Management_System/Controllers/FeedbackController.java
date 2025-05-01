package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.Entities.Feedback;
import com.AirlineManagement.Airline_Management_System.Services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitFeedback(@RequestBody Feedback feedback) {
        try {
            Feedback savedFeedback = feedbackService.submit(feedback);
            return ResponseEntity.ok(savedFeedback);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to submit feedback. Please try again.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while submitting feedback.");
        }
    }

    @GetMapping("/get-feedbacks")
    public ResponseEntity<?> getFeedbacks() {
        try {
            return ResponseEntity.ok(feedbackService.get());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to retrieve feedbacks.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while fetching feedbacks.");
        }
    }

    @GetMapping("/get-feedbacks/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(feedbackService.getFeedbacks(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to retrieve feedback for the given ID.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred while retrieving feedback.");
        }
    }
}
