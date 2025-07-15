import React, { createContext, useContext, useEffect, useState } from "react";

const UserContext = createContext();

export const UserProvider = ({ children }) => {
  const [username, setUsername] = useState(() => {
    // Initialize username from localStorage on component mount
    return localStorage.getItem("username") || null;
  });

  // Derive isLoggedIn directly from username
  // If username is not null and not an empty string, the user is considered logged in.
  const isLoggedIn = !!username;

  useEffect(() => {
    // This effect runs whenever 'username' changes.
    if (username) {
      // If username exists, save it to localStorage
      localStorage.setItem("username", username);
    } else {
      // If username is null (e.g., after logout), remove it from localStorage
      localStorage.removeItem("username");
    }
  }, [username]); // Dependency array: effect re-runs when username changes

  return (
    // Provide username, setUsername, AND the newly derived isLoggedIn state
    <UserContext.Provider value={{ username, setUsername, isLoggedIn }}>
      {children}
    </UserContext.Provider>
  );
};

// Custom hook to easily consume the UserContext in any component
export const useUser = () => useContext(UserContext);
