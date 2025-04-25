import React from "react";
import "./Navbar.css";
import { FaUserCircle } from "react-icons/fa";

const Navbar = () => {
  return (
    <nav className="maaz-navbar">
      <div className="maaz-navbar-logo">✈️ AirlineX</div>
      <ul className="maaz-navbar-links">
        <li><a href="#offers">Booking</a></li>
        <li><a href="#packages">Packages</a></li>
        <li><a href="#about">About Us</a></li>
        <li><a href="#help">Help</a></li>
      </ul>
      <div className="maaz-navbar-profile">
      <button className="maaz-user-btn"><FaUserCircle style={{ marginRight: "6px" }} /> User </button>
        <div className="maaz-dropdown">
          <a href="#profile">Profile</a>
          <a href="#notifications">Notifications</a>
          <a href="#booking-history">Booking History</a>
          <a href="#logout">Sign Out</a>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
