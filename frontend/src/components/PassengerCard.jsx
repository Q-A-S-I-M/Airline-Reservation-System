import React from 'react';
import './PassengerCard.css'; // Assuming you're styling separately

const PassengerCard = ({ index, passengerData, handleChange, errors }) => {
  return (
    <div className="maaz-passenger-card">
      <h3 className="maaz-passenger-number">Passenger {index + 1}</h3>

      <div className="maaz-passenger-fields-row">
        <div className="maaz-passenger-field">
          <label>First Name</label>
          <input
            type="text"
            name="firstName"
            value={passengerData.firstName}
            onChange={(e) => handleChange(index, e)}
          />
          {errors[index]?.firstName && <p className="maaz-passenger-error">{errors[index].firstName}</p>}
        </div>

        <div className="maaz-passenger-field">
          <label>Last Name</label>
          <input
            type="text"
            name="lastName"
            value={passengerData.lastName}
            onChange={(e) => handleChange(index, e)}
          />
          {errors[index]?.lastName && <p className="maaz-passenger-error">{errors[index].lastName}</p>}
        </div>
      </div>

      <div className="maaz-passenger-fields-row">
        <div className="maaz-passenger-field dob">
          <label>Date of Birth</label>
          <input
            type="date"
            name="dob"
            max={new Date().toISOString().split("T")[0]}
            value={passengerData.dob}
            onChange={(e) => handleChange(index, e)}
          />
          {errors[index]?.dob && <p className="maaz-passenger-error">{errors[index].dob}</p>}
        </div>

        <div className="maaz-passenger-field email">
          <label>Email</label>
          <input
            type="email"
            name="email"
            value={passengerData.email}
            onChange={(e) => handleChange(index, e)}
          />
          {errors[index]?.email && <p className="maaz-passenger-error">{errors[index].email}</p>}
        </div>
      </div>
    </div>
  );
};

export default PassengerCard;
