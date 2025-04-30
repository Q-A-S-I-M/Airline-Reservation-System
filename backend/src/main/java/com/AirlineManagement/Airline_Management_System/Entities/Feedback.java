package com.AirlineManagement.Airline_Management_System.Entities;

import jakarta.persistence.*;

import java.sql.Timestamp;


@Entity
@Table(name = "feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "flight_id", nullable = false)
    @ManyToOne
    private Flight flight;
    @JoinColumn(name = "username", nullable = false)
    @ManyToOne
    private User user;
    @Column(name = "rating", nullable = false)
    private int rating;
    @Column(name = "comments", nullable = false)
    private String comments;
    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
