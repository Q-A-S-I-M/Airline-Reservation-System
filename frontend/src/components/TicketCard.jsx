import React, { useRef } from "react";
import html2canvas from "html2canvas";
import jsPDF from "jspdf";
import "./TicketCard.css";

const TicketCard = ({ ticket }) => {
  if (!ticket || typeof ticket !== "object") {
    console.warn("Invalid ticket passed to TicketCard:", ticket);
    return <div className="ticket-error">Invalid ticket data</div>;
  }

  const {
    ticket: ticketId,
    flight,
    passenger,
    seat,
    status
  } = ticket;

  const cardRef = useRef();

  const handleDownloadPDF = async () => {
    try {
      const cardElement = cardRef.current;
      const button = cardElement?.querySelector(".download-btn");

      if (!cardElement) {
        console.error("Card element not found");
        return;
      }

      if (button) button.style.display = "none";

      const canvas = await html2canvas(cardElement);
      const imgData = canvas.toDataURL("image/png");

      if (button) button.style.display = "block";

      const pdf = new jsPDF("landscape", "pt", "a4");
      const width = pdf.internal.pageSize.getWidth();
      const height = (canvas.height * width) / canvas.width;

      pdf.addImage(imgData, "PNG", 20, 20, width - 40, height);
      pdf.save(`ticket_${ticketId}.pdf`);

      console.log(`PDF downloaded for ticket ID: ${ticketId}`);
    } catch (err) {
      console.error("PDF download failed:", err);
    }
  };

  return (
    <div>
      <div className="maaz-ticket-card-horizontal" ref={cardRef}>
        <div className="maaz-ticket-card-left">
          <h2>Ticket ID: {ticketId || "N/A"}</h2>
          <p><strong>Status:</strong> {status || "Unknown"}</p>
        </div>

        <div className="maaz-ticket-card-center">
          <p><strong>Flight ID:</strong> {flight?.id || "N/A"}</p>
          <p><strong>Seat:</strong> {seat?.seatNo || "N/A"}</p>
        </div>

        <div className="maaz-ticket-card-right">
          <p><strong>Passenger ID:</strong> {passenger?.id || "N/A"}</p>
          <p><strong>Name:</strong> {passenger?.firstName || ""} {passenger?.lastName || ""}</p>
          <button className="download-btn" onClick={handleDownloadPDF}>
            Download Ticket
          </button>
        </div>
      </div>
    </div>
  );
};

export default TicketCard;
