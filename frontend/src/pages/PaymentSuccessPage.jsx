import React from 'react';
import Navbar from '../components/Navbar.jsx';

const PaymentSuccessPage = () => {
  return (
    <div>
      <Navbar />
      <div className="maaz-success-container">
        <h1>Payment Successful!</h1>
        <p>Your payment has been processed successfully. Check your Booking History for details.</p>
      </div>
    </div>
  );
};

export default PaymentSuccessPage;
