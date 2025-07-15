import React, { useState, useRef } from "react";
import "./Navbar.css";
import { FaUserCircle } from "react-icons/fa";
import { useUser } from "../context/UserContext";
import { NavLink, useNavigate } from "react-router-dom";
import { ImAirplane } from "react-icons/im";
import api from "../api/axios";

const Navbar = () => {
  const { username, setUsername } = useUser();
  const navigate = useNavigate();
  const [showDropdown, setShowDropdown] = useState(false);
  const hideTimeoutRef = useRef(null);

  const HIDE_DELAY_MS = 50;

  const handleLogout = async () => {
    try{
      const response = await api.post('/auth/logout');
      console.log(response.data.message);
      localStorage.clear();
    }catch(error){
      console.log("Logout failed: "+error.response?.data|| error.message);
    }
  }

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
      <div className="maazz-navbar-logo">SkyNest <ImAirplane /></div>
      <ul className="maazz-navbar-links">
        <li><NavLink to={'/login/user'}>Booking</NavLink></li>
        <li><NavLink to={'/feedback'}>Feedback</NavLink></li>
        <li><NavLink to={'/view-tickets'}>Tickets</NavLink></li>
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
          <NavLink to={'/booking-history'} >Booking History</NavLink>
          <NavLink to={'/notifications'} >Notifications</NavLink>
          <NavLink to={'/'} onClick={handleLogout} >Sign Out</NavLink>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
