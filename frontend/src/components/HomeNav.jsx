import React from 'react';
import './HomeNav.css';
import { ImAirplane } from "react-icons/im";
import { NavLink } from 'react-router-dom';

const HomeNav = () => {
  return (
    <div className="HomeNav-container">
      <div className="name_logo">
        <ImAirplane />
        <h2>SkyNest</h2>
        <ImAirplane />
      </div>

      <div className="anchor">
        <NavLink to="/" className={({ isActive }) => isActive ? "active" : ""}>Home</NavLink>
        <NavLink to="/packages" className={({ isActive }) => isActive ? "active" : ""}>Packages</NavLink>
        <NavLink to="/login" className={({ isActive }) => isActive ? "active" : ""}>Login</NavLink>
      </div>
    </div>
  );
};

export default HomeNav;

