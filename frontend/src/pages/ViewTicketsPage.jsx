import React, { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import { useUser } from "../context/UserContext";
import TicketCard from "../components/TicketCard";
import axios from "axios";
import "./ViewTicketsPage.css";

const ViewTicketsPage = () => {
  const { username } = useUser();
  const [tickets, setTickets] = useState([]);
  const [statusFilter, setStatusFilter] = useState("All");

  useEffect(() => {
    if (username) {
      axios.get(`http://localhost:8080/tickets/user/${username}`)
        .then(res => setTickets(res.data))
        .catch(err => console.error(err));
    }
  }, [username]);

  const filteredTickets = statusFilter === "All"
    ? tickets
    : tickets.filter(ticket => ticket.status === statusFilter);

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
            onChange={e => setStatusFilter(e.target.value)}
          >
            <option value="All">All</option>
            <option value="Valid">Valid</option>
            <option value="Invalid">Invalid</option>
          </select>
        </div>

        <div className="maaz-ticket-cards">
          {filteredTickets.map(ticket => (
            <TicketCard key={ticket.ticket} ticket={ticket} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default ViewTicketsPage;
