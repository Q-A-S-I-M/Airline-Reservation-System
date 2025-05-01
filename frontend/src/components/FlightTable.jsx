import React, { useState, useEffect } from "react";
import { Check, Clock, X } from "lucide-react";
import "./FlightTable.css";
import api from '../api/axios'

export default function FlightTable() {

  const [flights, setFlights] = useState([]);


  const fetchData = async () => {
    try {
      const response = await api.get("http://localhost:8080/flights/get-flights");
      setFlights(response.data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };
  
  useEffect(() => {
    fetchData(); // Call fetchData inside useEffect
  }, []);

// useEffect(() => {
//   console.log("data changed"); // Logs updated flightsData after it changes
// }, [flights]);


  const [openDropdown, setOpenDropdown] = useState(null);
  
  // const [flights, setFlights] = useState([
  //   { id: 5, arrival: "2022-07-05 15:30:00", departure: "2022-07-05 12:30:00", destination: "Chiby", source: "Shiburn", airline: "Spark Airways", status: "scheduled" },
  //   { id: 6, arrival: "2022-07-05 17:55:00", departure: "2022-07-05 15:30:00", destination: "Chiby", source: "Weling", airline: "Spark Airways", status: "delayed" },
  //   { id: 7, arrival: "2022-07-05 20:50:00", departure: "2022-07-05 18:50:00", destination: "Odonhull", source: "Chiby", airline: "Spark Airways", status: "departed" },
  //   { id: 9, arrival: "2022-07-05 17:09:00", departure: "2022-07-05 16:05:00", destination: "Chiby", source: "Olisphis", airline: "Peak Airways", status: "cancelled" },
  //   { id: 11, arrival: "2022-07-05 19:10:00", departure: "2022-07-05 18:05:00", destination: "Oriaridge", source: "Flerough", airline: "Echo Airline", status: "landed" },
  //   { id: 12, arrival: "2022-07-05 21:10:00", departure: "2022-07-05 19:05:00", destination: "Chicago", source: "Yleigh", airline: "Peak Airways", status: "scheduled" },
  //   { id: 13, arrival: "2022-07-05 23:40:00", departure: "2022-07-05 20:51:00", destination: "Shiburn", source: "Oyladnard", airline: "Aero Airways", status: "scheduled" },
  // ]);

  const statuses = ["Scheduled", "Departed", "Delayed", "Cancelled", "Landed"];

  // const updateFlight = async (id, status) => {
  //   try {
  //     const response = await axios.put(`https://your-backend.com/api/flights/${flightData.id}`, flightData);
  //     console.log("Updated:", response.data);
  //   } catch (error) {
  //     console.error("Error updating user:", error);
  //   }
  // };

  // const updateFlightStatus = (id, status) => {
  //   setFlights(prev =>
  //     prev.map(flight => (flight.id === id ? { ...flight, status } : flight))
  //   );
  //   setOpenDropdown(null);

  //   updateFlight(id, status);
  // };


  // const updateFlight = async (id, status) => {
  //   try {
  //     const response = await axios.put(
  //       `http://localhost:8080/admin/update-flight/${id}`, 
  //       { status }  // Only sending the status field
  //     );
  //     console.log("Updated:", response.data);
  //   } catch (error) {
  //     console.error("Error updating flight:", error);
  //   }
  // };


  const updateFlight = async (id, status) => {
    try {
      const response = await api.put(
        `http://localhost:8080/flights/update-flight/${id}`,
        status, // sending "Departed" as raw string
        {
          headers: {
            'Content-Type': 'text/plain',
          },
        }
      );
      console.log("Updated:", response.data);
    } catch (error) {
      console.error("Error updating flight:", error);
    }
  };
  
  
  const updateFlightStatus = (id, status) => {
    setFlights(prev =>
      prev.map(flight => (flight.id === id ? { ...flight, status } : flight))
    );
    setOpenDropdown(null);
  
    updateFlight(id, status);
  };

  const toggleDropdown = (id) => {
    setOpenDropdown(openDropdown === id ? null : id);
  };

  const formatDate = (dateStr) => {
    const date = new Date(dateStr);
    return date.toLocaleString();
  };

  const getStatusBadge = (status) => {
    const classes = {
      Scheduled: "gray",
      Departed: "green",
      Delayed: "yellow",
      Cancelled: "red",
      Landed: "blue",
    };
    const icons = {
      Scheduled: <Clock className="status-icon" />,
      Departed: <Check className="status-icon" />,
      Delayed: <Clock className="status-icon" />,
      Cancelled: <X className="status-icon" />,
      Landed: <Check className="status-icon" />,
    };
    return (
      <span className={`status-badge ${classes[status]}`}>
        {icons[status]} {status.charAt(0).toUpperCase() + status.slice(1)}
      </span>
    );
  };

  const renderDropdown = (flight) => {
    const availableStatuses = {
      Scheduled: ["Departed", "Delayed", "Cancelled"],
      Departed: ["Landed"],
      Delayed: ["Departed"],
      Cancelled: [],
      Landed: [],
    };

    const options = availableStatuses[flight.status];

    return (
      <div className="dropdown-container">
        <button onClick={() => toggleDropdown(flight.id)} className="status-button">
          {getStatusBadge(flight.status)}
        </button>
        {openDropdown === flight.id && options.length > 0 && (
          <div className="dropdown-menu">
            {options.map((status) => (
              <button
                key={status}
                className="dropdown-item"
                onClick={() => updateFlightStatus(flight.id, status)}
              >
                {getStatusBadge(status)}
              </button>
            ))}
          </div>
        )}
      </div>
    );
  };

  const renderTable = (status) => (
    <>
      <h2>{status.charAt(0).toUpperCase() + status.slice(1)} Flights</h2>
      <table className="flight-table">
        <thead>
          <tr>
            <th>#</th>
            <th>Departure</th>
            <th>Arrival</th>
            <th>Source</th>
            <th>Destination</th>
            <th>Airline</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {flights.filter(f => f.status === status).map((flight) => (
            <tr key={flight.id}>
              <td>{flight.id}</td>
              <td>{formatDate(flight.departure)}</td>
              <td>{formatDate(flight.arrival)}</td>
              <td>{flight.fromLocation}</td>
              <td>{flight.toLocation}</td>
              <td>{flight.airline.name}</td>
              <td>{renderDropdown(flight)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );

  return (
    <div className="flight-tables-container">
      {statuses.map(status => renderTable(status))}
    </div>
  );
}
