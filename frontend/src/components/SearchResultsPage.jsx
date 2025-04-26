import React from 'react';
import { useLocation } from 'react-router-dom';
import Navbar from '../components/Navbar.jsx';
import './SearchResultsPage.css'; // You'll create this CSS file for styling

const SearchResultsPage = () => {
  const location = useLocation();
  const results = location.state?.results || [];

  return (
    <div>
      <Navbar />
      <h1 className="results-heading">Search Results</h1>
      <div className="results-container">
        {results.length === 0 ? (
          <p>No flights found.</p>
        ) : (
          results.map((flight) => (
            <div className="flight-cards" key={flight.id}>
              <h2>{flight.fromLocation} → {flight.toLocation}</h2>
              <p><strong>Departure:</strong> {new Date(flight.departure).toLocaleString()}</p>
              <p><strong>Arrival:</strong> {new Date(flight.arrival).toLocaleString()}</p>
              <p><strong>Airline:</strong> {flight.airline.name}</p>
              <p><strong>Aircraft:</strong> {flight.aircraft.model}</p>
              <p><strong>Price:</strong> ${flight.price}</p>
              <p><strong>Duration:</strong> {flight.duration}</p>
              <p><strong>Seats Booked:</strong> {flight.bookedSeats}/{flight.totalSeats}</p>
              <p><strong>Status:</strong> {flight.status}</p>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default SearchResultsPage;

