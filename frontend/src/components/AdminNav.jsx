import React from 'react'
import './AdminNav.css';
import { NavLink } from 'react-router-dom';
import { MdLogout } from "react-icons/md";

const AdminNav = () => {
  return (
    <div>
      <div className="AdminNav-Container">
        <div className="Admin-Name">
            <p>Admin Panel</p>
        </div>

        <div className="Admin-Tabs">
        {<NavLink to="/login/admin" className={({ isActive }) => isActive ? "active" : ""}>Dashboard</NavLink>}
        {<NavLink to="/admin/Flights" className={({ isActive }) => isActive ? "active" : ""}>Flights</NavLink>}
        {<NavLink to="/admin/Add-Flights" className={({ isActive }) => isActive ? "active" : ""}>Add Flight</NavLink> }
        {<NavLink to="/admin/Requests" className={({ isActive }) => isActive ? "active" : ""}>Requests</NavLink> }
        {<NavLink to="/admin/Reviews" className={({ isActive }) => isActive ? "active" : ""}>Reviews</NavLink> }
        </div>

        <div className="Admin-LogOut">
        {<NavLink to="/login">Log Out</NavLink>}
        <MdLogout className='logout-logo'/>
        </div>

      </div>
    </div>
  )
}

export default AdminNav
