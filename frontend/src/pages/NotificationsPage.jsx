import React, { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import NotificationCard from "../components/NotificationCard";
import { useUser } from "../context/UserContext";
import "./NotificationsPage.css";
import api from "../api/axios";

const NotificationsPage = () => {
  const { username } = useUser();
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    if (!username) return;
    
    api.get(`/notifications/user/${username}`, { withCredentials: true })
      .then((res) => {
        setNotifications(res.data);
      })
      .catch((err) => {
        console.error("Error fetching notifications:", err);
      });
  }, [username]);


  const handleDismiss = (id) => {
    setNotifications(notifications.filter((n) => n.id !== id));
  };

  return (
    <>
      <Navbar />
      <div className="maaz-notifications-container">
        {notifications.length === 0 ? (
          <p className="maaz-empty-text">No notifications to show.</p>
        ) : (
          notifications.map((notification) => (
            <NotificationCard
              key={notification.id}
              notification={notification}
              onDismiss={handleDismiss}
            />
          ))
        )}
      </div>
    </>
  );
};

export default NotificationsPage;
