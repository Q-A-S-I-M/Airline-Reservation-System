import React from 'react';
import './HomeNav.css';
import { ImAirplane } from "react-icons/im";
import { NavLink } from 'react-router-dom';

const HomeNav = () => {
  return (
    <div className="HomeNav-container">
      <div className="name_logo">
        <h2>SkyNest</h2>
        <ImAirplane />
      </div>
    </div>
  );
};

export default HomeNav;

