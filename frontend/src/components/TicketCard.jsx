import React from "react";
import "./TicketCard.css";

const TicketCard = ({ ticket }) => {
  const {
    ticket: ticketId,
    flight,
    passenger,
    seat,
    status
  } = ticket;

  return (
    <div className="maaz-ticket-card-horizontal">
      <div className="maaz-ticket-card-left">
        <h2>Ticket ID: {ticketId}</h2>
        <p><strong>Status:</strong> {status}</p>
      </div>

      <div className="maaz-ticket-card-center">
        <p><strong>Flight ID:</strong> {flight?.id}</p>
        <p><strong>Seat:</strong> {seat?.seatNo}</p>
      </div>

      <div className="maaz-ticket-card-right">
        <p><strong>Passenger ID:</strong> {passenger?.id}</p>
        <p><strong>Name:</strong> {passenger?.firstName} {passenger?.lastName}</p>
      </div>
    </div>
  );
};

export default TicketCard;
