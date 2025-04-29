import React from "react";
import "./BookingCard.css";
import axios from "axios";
import { useUser } from "../context/UserContext";
import { useNavigate } from 'react-router-dom';

const BookingCard = ({ booking }) => {
  const { id, timestamp, amount, paymentDeadline, status } = booking;
  const username = useUser();
  const navigate = useNavigate();

  const handlePayment = async () => {
    try {
      await axios.post("http://localhost:8080/payments", {
        id: id,
        user: { username },
      });
      navigate('/payment-success');
    } catch {
      console.error("Error processing payment");
    }
  };

  const handleCancel = () => {
    // Cancellation logic (if applicable)
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
            <button onClick={handleCancel} className="maaz-booking-button cancel">Cancel</button>
            <button className="maaz-booking-button pay disabled" disabled>Pay</button>
          </>
        );
      case "Approved":
        return <button onClick={handleCancel} className="maaz-booking-button cancel">Cancel</button>;
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
