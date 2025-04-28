import React from 'react';
import { useLocation } from 'react-router-dom';
import Navbar from '../components/Navbar.jsx';
import FlightCard from '../components/FlightCard.jsx'; // import here
import './SearchResultsPage.css';

const SearchResultsPage = () => {
  const location = useLocation();
  const results = location.state?.results || [];
  const seats = location.state?.seats || 1; // get seats passed from previous page

  return (
    <div>
      <Navbar />
      <h1 className="maaz-search-results-heading">Search Results</h1>
      <div className="maaz-search-results-container">
        {results.length === 0 ? (
          <p>No flights found.</p>
        ) : (
          results.map((flight) => (
            <FlightCard key={flight.id} flight={flight} seats={seats} />
          ))
        )}
      </div>
    </div>
  );
};

export default SearchResultsPage;
