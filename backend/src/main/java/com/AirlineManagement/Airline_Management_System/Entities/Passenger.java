package com.AirlineManagement.Airline_Management_System.Entities;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "passengers")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "contact")
    private String contact;
    @Column(name = "dob")
    private Date dob;
    // @JoinColumn(name = "username")
    // @ManyToOne
    // private User user;
    @OneToOne
    @PrimaryKeyJoinColumn(name="booking_id")
    private Booking booking;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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
