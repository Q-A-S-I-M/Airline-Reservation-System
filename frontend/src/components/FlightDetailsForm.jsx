import React, { useState, useEffect } from "react";
import "./FlightDetailsForm.css";
import api from "../api/axios";

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
      const response = await api.get("/flights/load-flightCreation");
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
    aircraft: { id: "", model: "", seats: "", status: "" },
  });

  const hours = Array.from({ length: 12 }, (_, i) =>
    String(i === 0 ? 12 : i).padStart(2, "0")
  );

  const formatDateTime = (date, time, ampm) => {
    if (!date || !time) return "";

    let hour = parseInt(time);
    if (ampm === "PM" && hour !== 12) hour += 12;
    if (ampm === "AM" && hour === 12) hour = 0;

    const paddedHour = String(hour).padStart(2, "0");

    // Get timezone offset
    const offsetMinutes = new Date().getTimezoneOffset();
    const sign = offsetMinutes <= 0 ? "+" : "-";
    const absMinutes = Math.abs(offsetMinutes);
    const offsetHours = String(Math.floor(absMinutes / 60)).padStart(2, "0");
    const offsetMins = String(absMinutes % 60).padStart(2, "0");
    const offset = `${sign}${offsetHours}:${offsetMins}`;

    return `${date}T${paddedHour}:00:00${offset}`;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validate date is not in the past
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const depDate = new Date(departureDate);
    const arrDate = new Date(arrivalDate);

    if (depDate < today) {
      alert("Departure date cannot be in the past.");
      return;
    }

    if (arrDate < today) {
      alert("Arrival date cannot be in the past.");
      return;
    }

    const formattedDeparture = formatDateTime(
      departureDate,
      departureTime,
      amPmDeparture
    );
    const formattedArrival = formatDateTime(
      arrivalDate,
      arrivalTime,
      amPmArrival
    );

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
      const response = await api.post("/flights/create-flight", updatedFlightData);
      console.log("Flight created successfully:", response.data);
      alert("Flight created successfully!");

      // Reset form
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

      // Auto-reload
      setTimeout(() => {
        window.location.reload();
      }, 1000);
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
            min={new Date().toISOString().split("T")[0]} // ✅ disable past dates
            onChange={(e) => setDepartureDate(e.target.value)}
            className="input"
          />
          <div className="time-picker">
            <select
              className="dropdown"
              value={departureTime}
              onChange={(e) => setDepartureTime(e.target.value)}
            >
              <option value="">--</option>
              {hours.map((h) => (
                <option key={h} value={h}>
                  {h}
                </option>
              ))}
            </select>
            <select
              className="dropdown"
              value={amPmDeparture}
              onChange={(e) => setAmPmDeparture(e.target.value)}
            >
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
            min={new Date().toISOString().split("T")[0]} // ✅ disable past dates
            onChange={(e) => setArrivalDate(e.target.value)}
            className="input"
          />
          <div className="time-picker">
            <select
              className="dropdown"
              value={arrivalTime}
              onChange={(e) => setArrivalTime(e.target.value)}
            >
              <option value="">--</option>
              {hours.map((h) => (
                <option key={h} value={h}>
                  {h}
                </option>
              ))}
            </select>
            <select
              className="dropdown"
              value={amPmArrival}
              onChange={(e) => setAmPmArrival(e.target.value)}
            >
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
          <select
            className="dropdown"
            value={flightData.aircraft.id}
            onChange={(e) => {
              const selected = Airline_Aircraft.aircrafts.find(
                (ac) => ac.id === Number(e.target.value)
              );
              if (selected) {
                setFlightData({
                  ...flightData,
                  aircraft: {
                    id: selected.id,
                    model: selected.model,
                    seats: selected.seats,
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

          <input
            className="input"
            placeholder="Price"
            type="number"
            value={flightData.price}
            onChange={(e) =>
              setFlightData({ ...flightData, price: e.target.value })
            }
          />

          <select
            className="dropdown"
            value={flightData.airline.id}
            onChange={(e) => {
              const selected = Airline_Aircraft.airlines.find(
                (al) => al.id === Number(e.target.value)
              );
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
          <button type="submit" className="submit-button">
            Proceed
          </button>
        </div>
      </div>
    </form>
  );
}
