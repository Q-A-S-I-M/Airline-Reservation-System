package com.AirlineManagement.Airline_Management_System.Entities;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type")
    private String type;
    @Column(name = "description")
    private String description;
    // @JoinColumn(name = "username")
    // @ManyToOne
    // private User user;
    @JoinColumn(name = "booking_id")
    @ManyToOne
    private Booking booking;
    @Column(name = "for_admin")
    private String forAdmin;
    @Column(name = "status")
    private String status;
    @Column(name = "timestamp")
    private Date timestamp;
    public void setTimestamp(Date timestamp){
        this.timestamp = timestamp;
    }
    public Date getTimestamp(){
        return timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getforAdmin() {
        return forAdmin;
    }

    public void setforAdmin(String forAdmin) {
        this.forAdmin = forAdmin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // public User getUser() {
    //     return user;
    // }

    // public void setUser(User user) {
    //     this.user = user;
    // }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
