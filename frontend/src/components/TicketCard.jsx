import React, { useRef } from "react";
import html2canvas from "html2canvas";
import jsPDF from "jspdf";
import "./TicketCard.css";

const TicketCard = ({ ticket }) => {
  const {
    ticket: ticketId,
    flight,
    passenger,
    seat,
    status
  } = ticket;

  const cardRef = useRef();

  const handleDownloadPDF = async () => {
    const cardElement = cardRef.current;
    const button = cardElement.querySelector(".download-btn");
  
    // Hide the button
    if (button) button.style.display = "none";
  
    // Convert the card to canvas
    const canvas = await html2canvas(cardElement);
    const imgData = canvas.toDataURL("image/png");
  
    // Show the button again
    if (button) button.style.display = "block";
  
    // Create PDF and add image
    const pdf = new jsPDF("landscape", "pt", "a4");
    const width = pdf.internal.pageSize.getWidth();
    const height = (canvas.height * width) / canvas.width;
  
    pdf.addImage(imgData, "PNG", 20, 20, width - 40, height);
    pdf.save(`ticket_${ticketId}.pdf`);
  };
  

  return (
    <div>
      <div className="maaz-ticket-card-horizontal" ref={cardRef}>
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
          <button className="download-btn" onClick={handleDownloadPDF}>Download Ticket</button>
        </div>
      </div>
    </div>
  );
};

export default TicketCard;
