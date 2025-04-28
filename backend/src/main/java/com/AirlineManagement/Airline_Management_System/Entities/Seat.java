package com.AirlineManagement.Airline_Management_System.Entities;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @Column(name = "seat_no")
    private String seatNo;
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "aircraft_id", foreignKey = @ForeignKey(
        name = "fk_seat_aircraft",
        foreignKeyDefinition = "FOREIGN KEY (aircraft_id) REFERENCES aircrafts(id) ON DELETE SET NULL"))
    @ManyToOne
    private AirCraft aircraft;

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AirCraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(AirCraft aircraft) {
        this.aircraft = aircraft;
    }
}
