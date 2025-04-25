import React from "react";
import FlightDetailsForm from '../components/FlightDetailsForm'// adjust the path as needed
import "./AdminAddFlights.css";
import AdminNav from '../components/AdminNav';

export default function AdminAddFlights() {
  return (
    <>
    <AdminNav/>
    <main className="home-container">
      <FlightDetailsForm />
    </main>
    </>
  );
}