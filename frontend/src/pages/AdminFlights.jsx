import React from "react";
import AdminNav from "../components/AdminNav";
import FlightTable from "../components/FlightTable";
import "./AdminFlights.css";

const AdminFlights = () => {
  return (
    <div className="admin-flights-wrapper">
      <AdminNav />
      <div className="admin-flights-content">
        <h1>Admin Flight Dashboard</h1>
        <FlightTable />
      </div>
    </div>
  );
};

export default AdminFlights;
