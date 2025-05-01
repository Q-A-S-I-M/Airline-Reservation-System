import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import Navbar from '../components/Navbar.jsx';
import PassengerCard from '../components/PassengerCard.jsx';
import './PassengerDetailsPage.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useUser } from '../context/UserContext';

const PassengerDetailsPage = () => {
  const { username } = useUser();
  const location = useLocation();
  const navigate = useNavigate();
  const { flight, seats } = location.state || {};

  const [passengers, setPassengers] = useState(
    Array.from({ length: seats }, () => ({
      firstName: '',
      lastName: '',
      dob: '',
      email: '',
    }))
  );

  const [errors, setErrors] = useState(
    Array.from({ length: seats }, () => ({})
  ));

  const handleChange = (index, event) => {
    const { name, value } = event.target;
    const updatedPassengers = [...passengers];
    updatedPassengers[index][name] = value;
    setPassengers(updatedPassengers);
  };

  const validatePassenger = (passenger) => {
    const errors = {};

    if (!/^[A-Za-z]+$/.test(passenger.firstName)) {
      errors.firstName = 'First name must contain only letters';
    }

    if (!/^[A-Za-z]+$/.test(passenger.lastName)) {
      errors.lastName = 'Last name must contain only letters';
    }

    if (!passenger.dob) {
      errors.dob = 'Date of birth is required';
    }

    if (!passenger.email.includes('@')) {
      errors.email = 'Email must contain @';
    }

    return errors;
  };

  const handleSubmit = async () => {
    const allErrors = passengers.map(validatePassenger);

    const hasErrors = allErrors.some((errorObj) => Object.keys(errorObj).length > 0);

    if (hasErrors) {
      setErrors(allErrors);
      console.log('Validation failed!');
      return;
    }

    const payload = {
      passengers: passengers,
      booking: {
        user: {
          username: username,
        },
        flight: flight,
      },
    };

    try {
      const response = await axios.post("http://localhost:8080/bookings/create", payload);
      const booking = response.data;
      console.log(response.data);
      navigate("/booking-details", { state: { booking } });
    } catch (error) {
      console.error("Booking creation failed", error);
      alert("Booking failed. Please try again.");
    }
  };

  return (
    <div>
      <Navbar />
      <h1 className="maaz-passenger-results-heading">Passenger Details</h1>

      <div className="maaz-passenger-cards-container">
        {passengers.map((passenger, index) => (
          <PassengerCard
            key={index}
            index={index}
            passengerData={passenger}
            handleChange={handleChange}
            errors={errors}
          />
        ))}
      </div>

      <div className="maaz-submit-container">
        <button className="maaz-submit-button" onClick={handleSubmit}>
          Submit
        </button>
      </div>
    </div>
  );
};

export default PassengerDetailsPage;
