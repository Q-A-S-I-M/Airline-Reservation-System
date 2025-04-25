import React from 'react';
import { useLocation } from 'react-router-dom';
import Navbar from '../components/Navbar.jsx';

const SearchResultsPage = () => {
  const location = useLocation();
  const results = location.state?.results || [];

  return (
    <div>
      <Navbar />
      <h1>Search Results Page</h1>
    </div>
  );
};

export default SearchResultsPage;
