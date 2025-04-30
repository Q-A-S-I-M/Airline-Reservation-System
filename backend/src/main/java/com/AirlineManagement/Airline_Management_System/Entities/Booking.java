package com.AirlineManagement.Airline_Management_System.Entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;
    @Column(name = "amount", nullable = false)
    private double amount;
    @Column(name = "payment_deadline", nullable = false)
    private Timestamp paymentDeadline;
    @Column(name = "status", nullable = false)
    private String status;
    @JoinColumn(name = "username", nullable = false)
    @ManyToOne
    private User user;
    @JoinColumn(name = "flight_id", nullable = false)
    @ManyToOne
    private Flight flight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp now) {
        this.timestamp = now;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setPaymentDeadline(Timestamp paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
