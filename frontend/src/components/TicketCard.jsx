import React from "react";
import "./BookingCard.css"; // Reuse same styles

const TicketCard = ({ ticket }) => {
  const {
    ticket: ticketId,
    flight,
    passenger,
    seat,
    status
  } = ticket;

  return (
    <div className="maaz-booking-card-horizontal">
      <div className="maaz-booking-card-left">
        <h2>Ticket ID: {ticketId}</h2>
        <p><strong>Status:</strong> {status}</p>
      </div>

      <div className="maaz-booking-card-center">
        <p><strong>Flight ID:</strong> {flight?.id}</p>
        <p><strong>Seat:</strong> {seat?.seatNo}</p>
      </div>

      <div className="maaz-booking-card-right">
        <p><strong>Passenger ID:</strong> {passenger?.id}</p>
        <p><strong>Name:</strong> {passenger?.firstName} {passenger?.lastName}</p>
      </div>
    </div>
  );
};

export default TicketCard;
