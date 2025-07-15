import React, { useEffect, useState } from 'react';
import api from '../api/axios'
import AdminNav from '../components/AdminNav';
import './AdminReviews.css'; // Keep your CSS file

const AdminReviews = () => {
  const [flights, setFlights] = useState([]);
  const [reviews, setReviews] = useState([]);
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await api.get("/feedbacks/get-feedbacks");
        setFlights(response.data);
      } catch (error) {
        console.error("Error fetching flight data:", error);
      }
    };

    getData();
  }, []);

  const getReviews = async (id) => {
    try {
      const response = await api.get(`/feedbacks/get-feedbacks/${id}`);
      setReviews(response.data);
      setShowModal(true);
    } catch (error) {
      console.error("Error fetching review data:", error);
    }
  };

  const closeModal = () => {
    setShowModal(false);
    setReviews([]);
  };

  return (
    <div>
      <AdminNav />
      <div className="reviews-container">
        {flights.map((flight) => (
          <div key={flight.id} className="flight-card">
            <div className="left-section">
              <h2 className="airline-name">{flight.airline.name}</h2>
              <p><strong>Flight ID:</strong> {flight.id}</p>
              <p><strong>From:</strong> {flight.fromLocation} → <strong>To:</strong> {flight.toLocation}</p>
              <p><strong>Departure:</strong> {new Date(flight.departure).toLocaleString()}</p>
              <p><strong>Arrival:</strong> {new Date(flight.arrival).toLocaleString()}</p>
            </div>
            <div className="right-section">
              <p><strong>Aircraft:</strong> {flight.aircraft.model}</p>
              <button onClick={() => getReviews(flight.id)} className="review-button">Show Reviews</button>
            </div>
          </div>
        ))}
      </div>

      {showModal && (
        <div className="modal-overlay">
          <div className="modal-content">
            <button className="close-button" onClick={closeModal}>X</button>
            <h2>Flight Reviews</h2>
            {reviews.length > 0 ? (
              reviews.map((review) => (
                <div key={review.id} className="review-card">
                  <div className="review-header">
                    <span className="review-username">{review.user?.username || "Unknown User"}</span>
                    <span className="review-timestamp">
                      {new Date(review.timestamp).toLocaleString()}
                    </span>
                  </div>
                  <p className="review-comment">{review.comments}</p>
                </div>
              ))              
            ) : (
              <p>No reviews found for this flight.</p>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default AdminReviews;
