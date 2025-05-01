import React, { useState } from "react";
import "./BookingCard.css";
import "./PaymentModal.css";
import api from '../api/axios';
import { useUser } from "../context/UserContext";
import { useNavigate } from 'react-router-dom';

const BookingCard = ({ booking }) => {
  const { id, timestamp, amount, paymentDeadline, status } = booking;
  const { username } = useUser();
  const navigate = useNavigate();

  const [showPaymentForm, setShowPaymentForm] = useState(false);
  const [paymentData, setPaymentData] = useState({
    name: '',
    cardNumber: '',
    cvv: '',
    amount: amount,
  });

  const handlePayment = () => {
    setShowPaymentForm(true);
  };

  const isValidCardNumber = (number) => {
    const trimmed = number.replace(/\s+/g, '');
    if (!/^\d{16}$/.test(trimmed)) return false;

    // Luhn algorithm
    let sum = 0;
    for (let i = 0; i < 16; i++) {
      let digit = parseInt(trimmed.charAt(15 - i), 10);
      if (i % 2 === 1) {
        digit *= 2;
        if (digit > 9) digit -= 9;
      }
      sum += digit;
    }
    return sum % 10 === 0;
  };

  const submitPayment = async (e) => {
    e.preventDefault();

    if (!isValidCardNumber(paymentData.cardNumber)) {
      alert("Invalid card number. Please enter a valid 16-digit card number.");
      return;
    }

    if (!/^\d{3,4}$/.test(paymentData.cvv)) {
      alert("Invalid CVV. It should be 3 or 4 digits.");
      return;
    }

    try {
      await api.post("http://localhost:8080/bookings/payment", {
        id: id,
        user: { username },
        // paymentInfo is intentionally not sent
      });

      setShowPaymentForm(false);
      navigate('/payment-success');
    } catch (error) {
      console.error("Error processing payment:", error);
      alert("Payment failed. Please try again.");
    }
  };

  const handleCancel = async () => {
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
    <>
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

      {showPaymentForm && (
        <div className="payment-modal-overlay">
          <div className="payment-modal">
            <h2>Payment Form</h2>
            <form onSubmit={submitPayment} className="payment-form">
              <label>
                Name on Card
                <input
                  type="text"
                  name="name"
                  value={paymentData.name}
                  onChange={(e) => setPaymentData({ ...paymentData, name: e.target.value })}
                  required
                />
              </label>

              <label>
                Card Number
                <input
                  type="text"
                  name="cardNumber"
                  value={paymentData.cardNumber}
                  onChange={(e) => setPaymentData({ ...paymentData, cardNumber: e.target.value })}
                  required
                  maxLength="16"
                  inputMode="numeric"
                />
              </label>

              <label>
                CVV
                <input
                  type="text"
                  name="cvv"
                  value={paymentData.cvv}
                  onChange={(e) => setPaymentData({ ...paymentData, cvv: e.target.value })}
                  required
                  maxLength="4"
                  inputMode="numeric"
                />
              </label>

              <label>
                Amount (PKR)
                <input
                  type="number"
                  name="amount"
                  value={paymentData.amount}
                  readOnly
                />
              </label>

              <div className="payment-form-buttons">
                <button type="submit">Pay Now</button>
                <button type="button" className="cancel-btn" onClick={() => setShowPaymentForm(false)}>Cancel</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </>
  );
};

export default BookingCard;

