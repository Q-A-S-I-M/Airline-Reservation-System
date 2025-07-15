import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./FlightSearchForm.css";
import Navbar from "../components/Navbar";
import HeroSection from "../components/Herosection";
import api from '../api/axios';

const FlightSearchForm = () => {
  const navigate = useNavigate();

  const [fromLocations, setFromLocations] = useState([]);
  const [toLocations, setToLocations] = useState([]);

  const [formData, setFormData] = useState({
    fromLocation: "",
    toLocation: "",
    departure: "",
    seats: 1,
    minRange: "",
    maxRange: "",
  });

  const [errorMessage, setErrorMessage] = useState("");

  // Fetch locations from backend
  useEffect(() => {
    const fetchLocations = async () => {
      try {
        const response = await api.get("/flights/flight-locations");
        setFromLocations(response.data.source || []);
        setToLocations(response.data.destination || []);
      } catch (error) {
        console.error("Error fetching locations:", error);
      }
    };

    fetchLocations();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const { minRange, maxRange } = formData;
    if (minRange !== "" && maxRange !== "" && parseFloat(maxRange) <= parseFloat(minRange)) {
      setErrorMessage("Max price must be greater than min price.");
      return;
    }

    setErrorMessage("");

    try {
      console.log("Form Data (Format1):", formData);

      const response = await api.post("/flights/search-flight", formData);

      console.log("Response from backend:", response.data);
      navigate("/search-results", {
        state: { results: response.data, seats: formData.seats },
      });
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  return (
    <>
      <Navbar />
      <HeroSection />
      <div className="maaz-flight-form-wrapper" id="maaz-flight-search-form">
        <h2>Search Flights</h2>
        <form className="maaz-flight-form" onSubmit={handleSubmit}>
          <label>From:
            <select
              required
              name="fromLocation"
              value={formData.fromLocation}
              onChange={handleChange}
            >
              <option value="" disabled>Select departure city</option>
              {fromLocations.map((city) => (
                <option key={city} value={city}>{city}</option>
              ))}
            </select>
          </label>
          <label>To:
            <select
              required
              name="toLocation"
              value={formData.toLocation}
              onChange={handleChange}
            >
              <option value="" disabled>Select destination city</option>
              {toLocations.map((city) => (
                <option key={city} value={city}>{city}</option>
              ))}
            </select>
          </label>
          <label>Departure Date:
            <input
              type="date"
              required
              name="departure"
              value={formData.departure}
              onChange={handleChange}
            />
          </label>
          <label>No. of Seats:
            <input
              id="seats"
              className="maaz-passengers-input"
              type="number"
              name="seats"
              value={formData.seats}
              onChange={handleChange}
              min="1"
              max="50"
              required
            />
          </label>
          <label>Min Price:
            <input
              type="number"
              name="minRange"
              value={formData.minRange}
              onChange={handleChange}
              min="1"
              className="maaz-price-input"
            />
          </label>
          <label>Max Price:
            <input
              type="number"
              name="maxRange"
              value={formData.maxRange}
              onChange={handleChange}
              min="1"
              className="maaz-price-input"
            />
          </label>
          <button type="submit" className="maaz-search-btn">Search</button>
        </form>
        {errorMessage && <p className="maaz-error-message">{errorMessage}</p>}
      </div>
    </>
  );
};

export default FlightSearchForm;