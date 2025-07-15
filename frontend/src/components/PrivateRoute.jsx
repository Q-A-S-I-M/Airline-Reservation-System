import React from "react";
import { Navigate } from "react-router-dom";
import { useUser } from "../context/UserContext";

const PrivateRoute = ({ children }) => {
  const { isLoggedIn } = useUser();

  return isLoggedIn ? children : <Navigate to="/" replace />;
};

export default PrivateRoute;
