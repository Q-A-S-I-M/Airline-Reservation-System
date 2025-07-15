// src/components/ProtectedRoutes.jsx
import { Outlet } from "react-router-dom";

const ProtectedRoutes = () => {
  return <Outlet />; // This will render nested protected routes
};

export default ProtectedRoutes;
