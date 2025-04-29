package com.AirlineManagement.Airline_Management_System.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticket;
    @JoinColumn(name = "passenger_id", nullable = false)
    @ManyToOne
    private Passenger passenger;
    @JoinColumn(name = "flight_id", nullable = false)
    @ManyToOne
    private Flight flight;
    @JoinColumn(name = "seat_no", nullable = false)
    @ManyToOne
    private Seat seat;
    @Column(name = "status", nullable = false)
    private String status;

    public Long getTicket() {
        return ticket;
    }

    public void setTicket(Long ticket) {
        this.ticket = ticket;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
