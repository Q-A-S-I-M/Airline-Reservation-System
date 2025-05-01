import React, { useState, useRef } from "react";
import "./Navbar.css";
import { FaUserCircle } from "react-icons/fa";
import { useUser } from "../context/UserContext";
import { useNavigate } from "react-router-dom";

const Navbar = () => {
  const { username, setUsername } = useUser();
  const navigate = useNavigate();
  const [showDropdown, setShowDropdown] = useState(false);
  const hideTimeoutRef = useRef(null);

  const HIDE_DELAY_MS = 50;

  const handleLogout = () => {
    setUsername(null);
    localStorage.removeItem("username");
    navigate('/login');
  };

  const handleBooking = () => {
    navigate('/login/user');
  };

  const handleFeedback = () => {
    navigate('/feedback');
  };

  const handleMouseEnter = () => {
    clearTimeout(hideTimeoutRef.current);
    setShowDropdown(true);
  };

  const handleMouseLeave = () => {
    hideTimeoutRef.current = setTimeout(() => {
      setShowDropdown(false);
    }, HIDE_DELAY_MS);
  };

  return (
    <nav className="maazz-navbar">
      <div className="maazz-navbar-logo">SkyNest</div>
      <ul className="maazz-navbar-links">
        <li><a onClick={handleBooking} href="#offers">Booking</a></li>
        <li><a onClick={handleFeedback} href="#feedback">Feedback</a></li>
      </ul>
      <div
        className="maazz-navbar-profile"
        onMouseEnter={handleMouseEnter}
        onMouseLeave={handleMouseLeave}
      >
        <button className="maazz-user-btn">
          <FaUserCircle style={{ marginRight: "6px" }} /> {username || "User"}
        </button>
        <div className={`maazz-dropdown ${showDropdown ? "show" : ""}`}>
          <a onClick={() => navigate('/booking-history')} href="#booking-history">Booking History</a>
          <a onClick={() => navigate('/view-tickets')} href="#tickets">Tickets</a>
          <a onClick={() => navigate('/notifications')} href="#notifications">Notifications</a>
          <a onClick={handleLogout} href="#logout">Sign Out</a>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
