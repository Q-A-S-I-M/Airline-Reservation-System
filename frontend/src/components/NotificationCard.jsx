import React from "react";
import "./NotificationCard.css";
import { formatTimestamp } from "../utils/timeUtils"; // Assuming you have a utility function for formatting timestamps

const NotificationCard = ({ notification, onDismiss }) => {
  const getBackgroundColor = (type) => {
    switch (type.toLowerCase()) {
      case "red": return "#ffe6e6";
      case "blue": return "#e6f0ff";
      default: return "#f4f4f4";
    }
  };

  return (
    <div
      className="maaz-notification-card"
      style={{ backgroundColor: getBackgroundColor(notification.type) }}
    >
      <button className="maaz-dismiss-button" onClick={() => onDismiss(notification.id)}>×</button>
      <p className="maaz-notification-text">{notification.description}</p>
      <p className="maaz-notification-time">{formatTimestamp(notification.timestamp)}</p>
    </div>
  );
};

export default NotificationCard;
