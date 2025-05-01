import React from 'react';
import Navbar from '../components/Navbar.jsx';
import './FeedbackSuccessPage.css';


const FeedbackSuccessPage = () => {
  return (
    <div>
      <Navbar />
      <div className="maaz-feedback-success-container">
        <h1>Feedback Submission Successful!</h1>
        <p>Your feedback has been submitted successfully.</p>
      </div>
    </div>
  );
};

export default FeedbackSuccessPage;
