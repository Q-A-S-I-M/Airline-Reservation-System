import React from "react";
import "./BookingCard.css";
import api from '../api/axios'
import { useUser } from "../context/UserContext";
import { useNavigate } from 'react-router-dom';

const BookingCard = ({ booking }) => {
  const { id, timestamp, amount, paymentDeadline, status } = booking;
  const { username } = useUser();
  const navigate = useNavigate();

  const handlePayment = async () => {
    try {
      await api.post("http://localhost:8080/bookings/payment", {
        id: id,
        user: { username },
      });
      navigate('/payment-success');
    } catch {
      console.error("Error processing payment");
    }
  };

  const handleCancel = async () => { // 🆕
    try {
      if (status === "Pending") {
        await api.put("http://localhost:8080/bookings/cancel", {
          id: id,
        });
      } else if (status === "Approved") {
        await api.post("http://localhost:8080/bookings/cancel-request", {
          id: id,
          user: { username },
        });
      }
      navigate('/cancel-success');
    } catch (error) {
      console.error("Error cancelling booking:", error);
      alert("Failed to cancel booking.");
    }
  };

  const renderButtons = () => {
    switch (status) {
      case "Pending":
        return (
          <>
            <button onClick={handleCancel} className="maaz-booking-button cancel">Cancel</button>
            <button onClick={handlePayment} className="maaz-booking-button pay">Pay</button>
          </>
        );
      case "Waiting for approval":
        return (
          <>
            <button className="maaz-booking-button cancel disabled" disabled>Cancel</button>
            <button className="maaz-booking-button pay disabled" disabled>Pay</button>
          </>
        );
      case "Approved":
        return <button onClick={handleCancel} className="maaz-booking-button cancel">Request Cancel</button>;
      default:
        return null;
    }
  };

  return (
    <div className="maaz-booking-card-horizontal">
      <div className="maaz-booking-card-left">
        <h2>Booking ID: {id}</h2>
        <p><strong>Status:</strong> {status}</p>
        <p><strong>Created:</strong> {new Date(timestamp).toLocaleString()}</p>
      </div>

      <div className="maaz-booking-card-center">
        <p><strong>Amount:</strong> PKR {amount}</p>
        <p><strong>Payment Deadline:</strong> {new Date(paymentDeadline).toLocaleString()}</p>
      </div>

      <div className="maaz-booking-card-right">
        {renderButtons()}
      </div>
    </div>
  );
};

export default BookingCard;
