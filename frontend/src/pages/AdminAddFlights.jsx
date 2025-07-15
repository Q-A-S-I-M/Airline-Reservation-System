import React from "react";
import AdminNav from "../components/AdminNav";
import FlightDetailsForm from "../components/FlightDetailsForm";
import "./AdminAddFlights.css";

const AddFlight = () => {
  return (
    <div className="add-flight-wrapper">
      <AdminNav />
      <FlightDetailsForm />
    </div>
  );
};

export default AddFlight;
