package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.Entities.Payment;
import com.AirlineManagement.Airline_Management_System.Repositories.PassengerRepository;
import com.AirlineManagement.Airline_Management_System.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PutMapping("/{id}")
    public void processPayment(@RequestBody long id) {
        paymentService.process(id);
    }

    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable Long id) {
        return paymentService.get(id);
    }
}
