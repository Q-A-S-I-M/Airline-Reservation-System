import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./FlightSearchForm.css";
import Navbar from "../components/Navbar";
import HeroSection from "../components/Herosection";
import axios from "axios";

const FlightSearchForm = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    fromLocation: "",
    toLocation: "",
    departureDateTime: "",
    seats: 1,
    minRange: "",
    maxRange: "",
  });

  const [errorMessage, setErrorMessage] = useState("");

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

      const response = await axios.get("http://localhost:8080/users/search-flight", {
        params: {
          fromLocation: formData.fromLocation,
          toLocation: formData.toLocation,
          departure: formData.departureDateTime,
          seats: formData.seats,
          minRange: parseInt(formData.minRange, 10),
          maxRange: parseInt(formData.maxRange, 10),
        },
      });

      navigate("/search-results", {
        state: { results: response.data },
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
            <input
              type="text"
              required
              name="fromLocation"
              value={formData.fromLocation}
              onChange={handleChange}
              placeholder="Enter departure city"
            />
          </label>
          <label>To:
            <input
              type="text"
              required
              name="toLocation"
              value={formData.toLocation}
              onChange={handleChange}
              placeholder="Enter destination city"
            />
          </label>
          <label>Departure Date:
            <input
              type="date"
              required
              name="departureDateTime"
              value={formData.departureDateTime}
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






















// import React, { useState } from "react";
// import { useNavigate } from "react-router-dom";
// import "./FlightSearchForm.css";
// import Navbar from "../components/Navbar";
// import HeroSection from "../components/Herosection";
// import axios from "axios";

// const FlightSearchForm = () => {
//   const navigate = useNavigate();

//   const [minRange, setMinRange] = useState("");
//   const [maxRange, setMaxRange] = useState("");
//   const [seats, setSeats] = useState(1);
//   const [fromLocation, setFromLocation] = useState("");
//   const [toLocation, setToLocation] = useState("");
//   const [departureDateTime, setDepartureDateTime] = useState("");
//   const [errorMessage, setErrorMessage] = useState("");

//   const handleSeatsChange = (e) => {
//     setSeats(e.target.value);
//   };

//   const handleMinRangeChange = (e) => {
//     const value = e.target.value;
//     if (value === "" || value > 0) {
//       setMinRange(value);
//     }
//   };

//   const handleMaxRangeChange = (e) => {
//     const value = e.target.value;
//     if (value === "" || value > 0) {
//       setMaxRange(value);
//     }
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();

//     if (minRange !== "" && maxRange !== "" && parseFloat(maxRange) <= parseFloat(minRange)) {
//       setErrorMessage("Max price must be greater than min price.");
//       return;
//     }

//     setErrorMessage("");

//     const formData = {
//       fromLocation,
//       toLocation,
//       departure: departureDateTime,
//       seats,
//       minRange: parseInt(minRange, 10),
//       maxRange: parseInt(maxRange, 10),
//     };

//     console.log("Form Data Submitted:", formData);

//     try {
//       const response = await axios.get("http://localhost:8080/users/search-flight", {
//         params: formData,
//       });

//       navigate("/search-results", {
//         state: {
//           results: response.data,
//         },
//       });
//     } catch (error) {
//       console.error("Error fetching data:", error);
//     }
//   };

//   return (
//     <>
//       <Navbar />
//       <HeroSection />
//       <div className="maaz-flight-form-wrapper" id="maaz-flight-search-form">
//         <h2>Search Flights</h2>
//         <form className="maaz-flight-form" onSubmit={handleSubmit}>
//           <label>From:
//             <input
//               type="text"
//               required
//               value={fromLocation}
//               onChange={(e) => setFromLocation(e.target.value)}
//               placeholder="Enter departure city"
//             />
//           </label>
//           <label>To:
//             <input
//               type="text"
//               required
//               value={toLocation}
//               onChange={(e) => setToLocation(e.target.value)}
//               placeholder="Enter destination city"
//             />
//           </label>
//           <label>Departure Date:
//             <input
//               type="date"
//               required
//               value={departureDateTime}
//               onChange={(e) => setDepartureDateTime(e.target.value)}
//             />
//           </label>
//           <label>No. of Seats:
//             <input
//               id="seats"
//               className="maaz-passengers-input"
//               type="number"
//               value={seats}
//               onChange={handleSeatsChange}
//               min="1"
//               max="50"
//               required
//             />
//           </label>
//           <label>Min Price:
//             <input
//               type="number"
//               value={minRange}
//               onChange={handleMinRangeChange}
//               min="1"
//               className="maaz-price-input"
//             />
//           </label>
//           <label>Max Price:
//             <input
//               type="number"
//               value={maxRange}
//               onChange={handleMaxRangeChange}
//               min="1"
//               className="maaz-price-input"
//             />
//           </label>
//           <button type="submit" className="maaz-search-btn">Search</button>
//         </form>
//         {errorMessage && <p className="maaz-error-message">{errorMessage}</p>}
//       </div>
//     </>
//   );
// };

// export default FlightSearchForm;







// import React, { useState } from "react";
// import { useNavigate } from "react-router-dom";
// import "./FlightSearchForm.css";
// import Navbar from "../components/Navbar";
// import HeroSection from "../components/Herosection";
// import axios from "axios";

// const destinations = ["Lahore", "Karachi", "Islamabad", "Dubai", "London"];

// const FlightSearchForm = () => {
//   const navigate = useNavigate();

//   const [minPrice, setMinPrice] = useState("");
//   const [maxPrice, setMaxPrice] = useState("");
//   const [passengers, setPassengers] = useState(1);
//   const [from, setFrom] = useState("");
//   const [to, setTo] = useState("");
//   const [departureDate, setDepartureDate] = useState("");
//   const [errorMessage, setErrorMessage] = useState("");

//   const handlePassengersChange = (e) => {
//     setPassengers(e.target.value);
//   };

//   const handleMinPriceChange = (e) => {
//     const value = e.target.value;
//     if (value === "" || value > 0) {
//       setMinPrice(value);
//     }
//   };

//   const handleMaxPriceChange = (e) => {
//     const value = e.target.value;
//     if (value === "" || value > 0) {
//       setMaxPrice(value);
//     }
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();

//     if (minPrice !== "" && maxPrice !== "" && parseFloat(maxPrice) <= parseFloat(minPrice)) {
//       setErrorMessage("Max price must be greater than min price.");
//       return;
//     }

//     setErrorMessage("");

//     const formData = {
//       from,
//       to,
//       departureDate,
//       passengers,
//       minPrice,
//       maxPrice
//     };

//     console.log("Form Data Submitted:", formData);

//     navigate("/search-results");

//       try {
//         const response = await axios.get("http://localhost:8080/users/search-flight", {
//           params: formData, // ✅ send as query params
//         });

//         navigate("/search-results", {
//           state: {
//             results: response.data
//             /*searchParams: formData // ✅ optional: pass form data to results page too*/
//           },
//         });
//       } catch (error) {
//         console.error("Error fetching data:", error);
//       }
//   };

//   return (
//     <>
//       <Navbar />
//       <HeroSection />
//       <div className="maaz-flight-form-wrapper" id="maaz-flight-search-form">
//         <h2>Search Flights</h2>
//         <form className="maaz-flight-form" onSubmit={handleSubmit}>
//           <label>From:
//             <select
//               required
//               value={from}
//               onChange={(e) => setFrom(e.target.value)}
//             >
//               <option value="" disabled>Select your departure city</option>
//               {destinations.map(dest => <option key={dest} value={dest}>{dest}</option>)}
//             </select>
//           </label>
//           <label>To:
//             <select
//               required
//               value={to}
//               onChange={(e) => setTo(e.target.value)}
//             >
//               <option value="" disabled>Select your destination city</option>
//               {destinations.map(dest => <option key={dest} value={dest}>{dest}</option>)}
//             </select>
//           </label>
//           <label>Departure Date:
//             <input type="date" required value={departureDate} onChange={(e) => setDepartureDate(e.target.value)} />
//           </label>
//           <label>No. of Passengers:
//             <input id="passengers" className="maaz-passengers-input" type="number" value={passengers} onChange={handlePassengersChange} min="1" max="50" required />
//           </label>
//           <label>Min Price:
//             <input
//               type="number"
//               value={minPrice}
//               onChange={handleMinPriceChange}
//               min="1"
//               className="maaz-price-input"
//             />
//           </label>
//           <label>Max Price:

//             <input
//               type="number"
//               value={maxPrice}
//               onChange={handleMaxPriceChange}
//               min="1"
//               className="maaz-price-input"
//             />
//           </label>
//           <button type="submit" className="maaz-search-btn">Search</button>
//         </form>
//         {errorMessage && <p className="maaz-error-message">{errorMessage}</p>}
//       </div>
//     </>
//   );
// };

// export default FlightSearchForm;

