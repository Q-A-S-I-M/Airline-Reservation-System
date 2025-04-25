import React from "react";
import "./HeroSection.css";
import airplaneImage from "../assets/herosectionairplane.jpg";

const HeroSection = () => {
  const scrollToForm = () => {
    const form = document.getElementById("flight-search-form");
    if (form) {
      form.scrollIntoView({ behavior: "smooth" });
    }
  };

  return (
    <div className="maaz-hero-section">
      <img
        src={airplaneImage}
        alt="Airplane"
        className="maaz-hero-image"
      />
      <div className="maaz-hero-text">
        <h1>Fly Smarter. Fly Faster. Fly AirlineX.</h1>
        <button onClick={scrollToForm} className="maaz-book-now-btn">Book Now</button>
      </div>
    </div>
  );
};

export default HeroSection;
