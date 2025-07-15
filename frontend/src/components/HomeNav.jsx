import React from 'react';
import './HomeNav.css';
import { ImAirplane } from "react-icons/im";
import { NavLink } from 'react-router-dom';

const HomeNav = () => {
  return (
    <div className="HomeNav-container">
      <div className="name-logo">
        <h2>SkyNest <ImAirplane /></h2>
      </div>
    </div>
  );
};

export default HomeNav;
