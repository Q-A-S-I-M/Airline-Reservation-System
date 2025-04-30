import React, { useState, useEffect } from "react";
import "./FlightDetailsForm.css";
import axios from "axios";

export default function FlightDetailsForm() {
    const [departureDate, setDepartureDate] = useState("");
    const [departureTime, setDepartureTime] = useState("");
    const [amPmDeparture, setAmPmDeparture] = useState("AM");

    const [arrivalDate, setArrivalDate] = useState("");
    const [arrivalTime, setArrivalTime] = useState("");
    const [amPmArrival, setAmPmArrival] = useState("AM");

    const [Airline_Aircraft, setAirline_Aircraft] = useState([]);

    const fetchData = async () => {
        try {
            const response = await axios.get("http://localhost:8080/flights/load-flightCreation");
            setAirline_Aircraft(response.data);
        } catch (error) {
            console.error("Error fetching data:", error);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    const [flightData, setFlightData] = useState({
        fromLocation: "",
        toLocation: "",
        departure: "",
        arrival: "",
        price: "",
        airline: { id: "", name: "" },
        aircraft: { id: "", model: "", seats: "", status: "" }, // note: seats here
    });

    const hours = Array.from({ length: 12 }, (_, i) => String(i === 0 ? 12 : i).padStart(2, "0"));

    const formatDateTime = (date, time, ampm) => {
        if (!date || !time) return "";
    
        let hour = parseInt(time);
        if (ampm === "PM" && hour !== 12) hour += 12;
        if (ampm === "AM" && hour === 12) hour = 0;
    
        const paddedHour = String(hour).padStart(2, "0");
    
        // Format: 2025-04-01T01:00:00
        return `${date}T${paddedHour}:00:00`;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
    
        const formattedDeparture = formatDateTime(departureDate, departureTime, amPmDeparture);
        const formattedArrival = formatDateTime(arrivalDate, arrivalTime, amPmArrival);
    
        if (isNaN(flightData.price) || flightData.price === "") {
            alert("Please enter a valid numeric price.");
            return;
        }
    
        const updatedFlightData = {
            ...flightData,
            departure: formattedDeparture,
            arrival: formattedArrival,
            price: parseInt(flightData.price, 10),
        };
    
        console.log("Submitted Flight Data:", updatedFlightData);
    
        try {
            const response = await axios.post("http://localhost:8080/flights/create-flight", updatedFlightData);
            console.log("Flight created successfully:", response.data);
            alert("Flight created successfully!");
    
            // ✅ Reset form fields
            setDepartureDate("");
            setDepartureTime("");
            setAmPmDeparture("AM");
            setArrivalDate("");
            setArrivalTime("");
            setAmPmArrival("AM");
            setFlightData({
                fromLocation: "",
                toLocation: "",
                departure: "",
                arrival: "",
                price: "",
                airline: { id: "", name: "" },
                aircraft: { id: "", model: "", seats: "", status: "" },
            });

                // ✅ Auto-reload after short delay
    setTimeout(() => {
        window.location.reload();
    }, 1000); // 1 second delay
    
        } catch (error) {
            console.error("Error creating flight:", error);
            alert("Failed to create flight. Please try again.");
        }
    };
    

    return (
        <form className="card" onSubmit={handleSubmit}>
            <h1 className="form-title">ADD FLIGHT DETAILS</h1>
            <div className="form-grid">
                {/* Departure */}
                <div className="row">
                    <label className="label">DEPARTURE</label>
                    <input
                        type="date"
                        value={departureDate}
                        onChange={(e) => setDepartureDate(e.target.value)}
                        className="input"
                    />
                    <div className="time-picker">
                        <select className="dropdown" value={departureTime} onChange={(e) => setDepartureTime(e.target.value)}>
                            <option value="">--</option>
                            {hours.map((h) => (
                                <option key={h} value={h}>{h}</option>
                            ))}
                        </select>
                        <select className="dropdown" value={amPmDeparture} onChange={(e) => setAmPmDeparture(e.target.value)}>
                            <option value="AM">AM</option>
                            <option value="PM">PM</option>
                        </select>
                    </div>
                </div>

                {/* Arrival */}
                <div className="row">
                    <label className="label">ARRIVAL</label>
                    <input
                        type="date"
                        value={arrivalDate}
                        onChange={(e) => setArrivalDate(e.target.value)}
                        className="input"
                    />
                    <div className="time-picker">
                        <select className="dropdown" value={arrivalTime} onChange={(e) => setArrivalTime(e.target.value)}>
                            <option value="">--</option>
                            {hours.map((h) => (
                                <option key={h} value={h}>{h}</option>
                            ))}
                        </select>
                        <select className="dropdown" value={amPmArrival} onChange={(e) => setAmPmArrival(e.target.value)}>
                            <option value="AM">AM</option>
                            <option value="PM">PM</option>
                        </select>
                    </div>
                </div>

                {/* Source and Destination */}
                <div className="row">
                    <input
                        className="input"
                        placeholder="From (e.g. New York JFK)"
                        type="text"
                        value={flightData.fromLocation}
                        onChange={(e) =>
                            setFlightData({ ...flightData, fromLocation: e.target.value })
                        }
                    />
                    <input
                        className="input"
                        placeholder="To (e.g. Los Angeles LAX)"
                        type="text"
                        value={flightData.toLocation}
                        onChange={(e) =>
                            setFlightData({ ...flightData, toLocation: e.target.value })
                        }
                    />
                </div>

                {/* Aircraft & Airline */}
                <div className="row">
                    {/* Aircraft Dropdown */}
                    <select
                        className="dropdown"
                        value={flightData.aircraft.id}
                        onChange={(e) => {
                            const selected = Airline_Aircraft.aircrafts.find(ac => ac.id === Number(e.target.value));
                            if (selected) {
                                setFlightData({
                                    ...flightData,
                                    aircraft: {
                                        id: selected.id,
                                        model: selected.model,
                                        seats: selected.seats, // ✅ fixed here
                                        status: selected.status,
                                    },
                                });
                            }
                        }}
                    >
                        <option value="">Select Aircraft</option>
                        {Airline_Aircraft.aircrafts?.map((ac) => (
                            <option key={ac.id} value={ac.id}>
                                {ac.model}
                            </option>
                        ))}
                    </select>

                    {/* Price Input */}
                    <input
                        className="input"
                        placeholder="Price"
                        type="number"
                        value={flightData.price}
                        onChange={(e) =>
                            setFlightData({ ...flightData, price: e.target.value })
                        }
                    />

                    {/* Airline Dropdown */}
                    <select
                        className="dropdown"
                        value={flightData.airline.id}
                        onChange={(e) => {
                            const selected = Airline_Aircraft.airlines.find(al => al.id === Number(e.target.value));
                            if (selected) {
                                setFlightData({
                                    ...flightData,
                                    airline: { id: selected.id, name: selected.name },
                                });
                            }
                        }}
                    >
                        <option value="">Select Airline</option>
                        {Airline_Aircraft.airlines?.map((al) => (
                            <option key={al.id} value={al.id}>
                                {al.name}
                            </option>
                        ))}
                    </select>
                </div>

                {/* Submit */}
                <div className="button-wrapper">
                    <button type="submit" className="submit-button">Proceed ➡️</button>
                </div>
            </div>
        </form>
    );
}

