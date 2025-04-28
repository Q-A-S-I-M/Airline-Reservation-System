import React from "react";
import "./FlightCard.css";
import { useNavigate, /*useLocation*/ } from 'react-router-dom';

const FlightCard = ({ flight, seats }) => {
    const navigate = useNavigate();

  const handleBookClick = () => {
    navigate("/passenger-details", {
      state: {
        flightId: flight.id,
        seats: seats,
      },
    });
  };

  return (
    <div className="maaz-flight-card-horizontal">
      <div className="maaz-flight-card-left">
        <h2>{flight.fromLocation} → {flight.toLocation}</h2>
        <p className="maaz-flight-id">ID: {flight.id}</p>
        <p><strong>Departure:</strong> {new Date(flight.departure).toLocaleString()}</p>
        <p><strong>Arrival:</strong> {new Date(flight.arrival).toLocaleString()}</p>
      </div>

      <div className="maaz-flight-card-center">
        <p><strong>Airline:</strong> {flight.airline.name}</p>
        <p><strong>Aircraft:</strong> {flight.aircraft.model}</p>
        <p><strong>Duration:</strong> {flight.duration}</p>
        <p><strong>Status:</strong> {flight.status}</p>
      </div>

      <div className="maaz-flight-card-right">
        <p><strong>Price:</strong> <span className="maaz-price">${flight.price}</span></p>
        <p><strong>Seats Booked:</strong> {flight.bookedSeats}/{flight.totalSeats}</p>
        <button className="maaz-book-button" onClick={handleBookClick}>Book</button>
      </div>
    </div>
  );
};

export default FlightCard;
