import React, { useState } from "react";
import Navbar from "../components/Navbar";
import api from "../api/axios";
import { useUser } from "../context/UserContext";
import "./FeedbackPage.css";
import { useNavigate } from 'react-router-dom';

const Feedback = () => {
  const { username } = useUser();
  const [flightId, setFlightId] = useState("");
  const [comments, setComments] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!flightId || !comments.trim()) return;

    try {
      setSubmitting(true);

      console.log("Submitting:", {
        flight: { id: parseInt(flightId) },
        user: { username },
        comments,
      });
      
      await api.post("http://localhost:8080/feedbacks/submit", {
        flight: { id: parseInt(flightId) },
        user: { username },
        comments,
      });
      navigate('/feedback-success');
    } catch (err) {
      console.error("Feedback error:", err);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <>
      <Navbar />
      <div className="maaz-feedback-container">
        <h1 className="maaz-feedback-title">Flight Feedback</h1>
        <form className="maaz-feedback-form" onSubmit={handleSubmit}>
          <label className="maaz-feedback-label">Flight ID:</label>
          <input
            type="text"
            inputMode="numeric"
            pattern="[1-9][0-9]*"
            value={flightId}
            onChange={(e) => {
                const value = e.target.value;
                if (/^[1-9][0-9]*$/.test(value) || value === "") {
                setFlightId(value);
                }
            }}
            required
            className="maaz-feedback-input maaz-no-spinner"
            />

          <label className="maaz-feedback-label">
            Comments (max 1000 characters):
          </label>
          <textarea
            value={comments}
            onChange={(e) => {
              if (e.target.value.length <= 1000) {
                setComments(e.target.value);
                e.target.style.height = "auto";
                e.target.style.height = `${e.target.scrollHeight}px`;
              }
            }}
            className="maaz-feedback-textarea"
            placeholder="How was your flight experience?"
            required
          />

          <button
            type="submit"
            className="maaz-feedback-button"
            disabled={submitting}
          >
            {submitting ? "Submitting..." : "Submit Feedback"}
          </button>
        </form>
      </div>
    </>
  );
};

export default Feedback;
