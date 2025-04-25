
import React, { useState } from "react";
import AdminNav from '../components/AdminNav';
import FlightTable from '../components/FlightTable'; // Import FlightTable component
import './AdminFlights.css'

const AdminFlights = () => {

  return (
    <div>
      <AdminNav />
      <div className="admin-flights-content">
        <h1>Admin Flight Dashboard</h1>
        <FlightTable /> {/* Add FlightTable component here */}
      </div>
    </div>
  );
}

export default AdminFlights;