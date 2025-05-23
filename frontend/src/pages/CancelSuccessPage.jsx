import React from 'react';
import Navbar from '../components/Navbar.jsx';
import './CancelSuccessPage.css';


const CancelSuccessPage = () => {
  return (
    <div>
      <Navbar />
      <div className="maaz-cancel-success-container">
        <h1>Cancellation Successful!</h1>
        <p>Your booking has been cancelled. Visit Booking History to confirm the status.</p>
      </div>
    </div>
  );
};

export default CancelSuccessPage;
