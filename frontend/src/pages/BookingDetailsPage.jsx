// BookingDetailsPage.jsx
import React from 'react';
import { useLocation } from 'react-router-dom';
import Navbar from '../components/Navbar.jsx';
import './BookingDetailsPage.css';

const BookingDetailsPage = () => {
  const location = useLocation();
  const { booking } = location.state || {};

  return (
    <div>
      <Navbar />
      <div className="maaz-booking-success-container">
        <h1>Booking Created Successfully!</h1>
        <p>Please visit the <strong>Booking History</strong> page to proceed with payment.</p>
        <div className="maaz-booking-details-card">
          <p><strong>Booking ID:</strong> {booking.id}</p>
          <p><strong>Amount:</strong> ${booking.amount}</p>
          <p><strong>Status:</strong> {booking.status}</p>
          <p><strong>Payment Deadline:</strong> {new Date(booking.paymentDeadline).toLocaleString()}</p>
          <p><strong>Flight ID:</strong> {booking.flight.id}</p>
          <p><strong>Aircraft:</strong> {booking.flight.aircraft.model}</p>
          <p><strong>Price per Seat:</strong> ${booking.flight.price}</p>
          <p><strong>Seats Booked:</strong> {booking.flight.bookedSeats}</p>
        </div>
      </div>
    </div>
  );
};

export default BookingDetailsPage;
