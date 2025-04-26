import React, { useEffect, useState } from 'react';
import axios from 'axios';
import AdminNav from '../components/AdminNav';
import './AdminReviews.css'; // Keep your CSS file

const AdminReviews = () => {
  const [flights, setFlights] = useState([]);
  const [reviews, setReviews] = useState([]);
  const [showReviews, setShowReviews] = useState(false);

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await axios.get("http://localhost:8080/admin/feedbacks");
        setFlights(response.data);
      } catch (error) {
        console.error("Error fetching flight data:", error);
      }
    };

    getData();
  }, []);

  const getReviews = async (id) => {
    try {
      const response = await axios.get(`http://localhost:8080/admin/feedbacks/${id}`);
      setReviews(response.data);
      setShowReviews(true);
    } catch (error) {
      console.error("Error fetching review data:", error);
    }
  };

  return (
    <div>
      <AdminNav />
      <div className="reviews-container">
        {!showReviews ? (
          flights.map((flight) => (
            <div key={flight.id} className="flight-card">
              <div className="left-section">
                <h2 className="airline-name">{flight.airline.name}</h2>
                <p><strong>From:</strong> {flight.fromLocation} → <strong>To:</strong> {flight.toLocation}</p>
                <p><strong>Departure:</strong> {new Date(flight.departure).toLocaleString()}</p>
                <p><strong>Arrival:</strong> {new Date(flight.arrival).toLocaleString()}</p>
              </div>
              <div className="right-section">
                <p><strong>Status:</strong> {flight.status}</p>
                <p><strong>Duration:</strong> {flight.duration}</p>
                <p><strong>Aircraft:</strong> {flight.aircraft.model}</p>
                <p><strong>Seats Booked:</strong> {flight.bookedSeats} / {flight.totalSeats}</p>
                <p><strong>Price:</strong> ${flight.price}</p>
                <button onClick={() => getReviews(flight.id)} className="review-button">Show Reviews</button>
              </div>
            </div>
          ))
        ) : (
          reviews.map((review) => (
            <div key={review.id} className="review-card">
              <p><strong>Rating:</strong> {review.rating}</p>
              <p><strong>Comment:</strong> {review.comments}</p>
              <p><strong>Timestamp:</strong> {new Date(review.timestamp).toLocaleString()}</p>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default AdminReviews;

