import React, { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import { useUser } from "../context/UserContext";
import TicketCard from "../components/TicketCard";
import axios from "../api/axios";
import "./ViewTicketsPage.css";

const ViewTicketsPage = () => {
  const { username } = useUser();
  const [tickets, setTickets] = useState([]);
  const [statusFilter, setStatusFilter] = useState("All");

  useEffect(() => {
  if (username) {
    const url = `/tickets/user/${username}`;
    console.log("📡 Sending GET request to:", url);

    axios.get(url)
      .then(res => {
        console.log("✅ Tickets response:", res.data);
        setTickets(res.data);
      })
      .catch(err => {
        console.error("❌ Error fetching tickets:", err);
      });
  }
}, [username]);


  let filteredTickets = [];

  try {
    filteredTickets =
      statusFilter === "All"
        ? tickets
        : tickets.filter((ticket) => ticket.status === statusFilter);

    if (!Array.isArray(filteredTickets)) {
      console.warn("Filtered tickets is not an array:", filteredTickets);
      filteredTickets = [];
    }
  } catch (err) {
    console.error("Filtering tickets failed:", err);
    filteredTickets = [];
  }

  return (
    <div>
      <Navbar />
      <div className="maaz-ticket-history-container">
        <h2>Your Tickets</h2>

        <div className="maaz-ticket-filter-section">
          <label htmlFor="status-filter">Filter by Status: </label>
          <select
            id="status-filter"
            value={statusFilter}
            onChange={(e) => setStatusFilter(e.target.value)}
          >
            <option value="All">All</option>
            <option value="Valid">Valid</option>
            <option value="Invalid">Invalid</option>
          </select>
        </div>

        <div className="maaz-ticket-cards">
          {filteredTickets.length > 0 ? (
            filteredTickets.map((ticket, index) => (
              <TicketCard key={ticket?.ticket || index} ticket={ticket} />
            ))
          ) : (
            <p className="no-tickets-msg">No tickets found.</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default ViewTicketsPage;
