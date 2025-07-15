import React from 'react';
import './AdminNav.css';
import { NavLink } from 'react-router-dom';
import { MdLogout } from "react-icons/md";
import api from '../api/axios';

const AdminNav = () => {
  const handleLogout = async () => {
    try{
      const response = await api.post('/auth/logout');
      console.log(response.data.message);
      localStorage.clear();
    }catch(error){
    const errMsg = error.response?.data?.error || error.message || "Unknown error";
    console.error("Logout failed:", errMsg);
  }

  }
  return (
    <div className="AdminNav-Container">
      <div className="Admin-Name">
        <p>Admin Panel</p>
      </div>

      <div className="Admin-Tabs">
        <NavLink to="/login/admin" className={({ isActive }) => isActive ? "active" : ""}>Dashboard</NavLink>
        <NavLink to="/admin/Flights" className={({ isActive }) => isActive ? "active" : ""}>Flights</NavLink>
        <NavLink to="/admin/Add-Flights" className={({ isActive }) => isActive ? "active" : ""}>Add Flight</NavLink>
        <NavLink to="/admin/Requests" className={({ isActive }) => isActive ? "active" : ""}>Requests</NavLink>
        <NavLink to="/admin/Reviews" className={({ isActive }) => isActive ? "active" : ""}>Reviews</NavLink>
      </div>

      <div className="Admin-LogOut">
        <NavLink to="/" onClick={handleLogout}>Log Out <MdLogout/></NavLink>
      </div>
    </div>
  );
};

export default AdminNav;
