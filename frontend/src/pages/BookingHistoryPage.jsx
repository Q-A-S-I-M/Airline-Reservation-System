import React, { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import { useUser } from "../context/UserContext";
import BookingCard from "../components/BookingCard";
import api from '../api/axios'
import "./BookingHistoryPage.css";

const BookingHistoryPage = () => {
  const { username } = useUser();
  const [bookings, setBookings] = useState([]);
  const [statusFilter, setStatusFilter] = useState("All");

  useEffect(() => {
    if (username) {
      api.get(`http://localhost:8080/bookings/${username}`)
        .then(res => setBookings(res.data))
        .catch(err => console.error(err));
    }
  }, [username]);

  const filteredBookings = statusFilter === "All"
    ? bookings
    : bookings.filter(b => b.status === statusFilter);

  return (
    <div>
      <Navbar />
      <div className="maaz-booking-history-container">
        <h2>Your Booking History</h2>

        <div className="maaz-filter-section">
          <label htmlFor="status-filter">Filter by Status: </label>
          <select
            id="status-filter"
            value={statusFilter}
            onChange={e => setStatusFilter(e.target.value)}
          >
            <option value="All">All</option>
            <option value="Pending">Pending</option>
            <option value="Waiting for approval">Waiting for approval</option>
            <option value="Approved">Approved</option>
            <option value="Deadline crossed">Deadline crossed</option>
            <option value="Cancelled">Cancelled</option>
          </select>
        </div>

        <div className="maaz-booking-cards">
          {filteredBookings.map(booking => (
            <BookingCard key={booking.id} booking={booking} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default BookingHistoryPage;
