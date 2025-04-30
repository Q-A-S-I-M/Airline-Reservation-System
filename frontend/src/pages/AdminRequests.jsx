import { useState } from "react"
import AdminNav from "../components/AdminNav"
import { Check, X, Clock } from "lucide-react"
import { formatDistanceToNow } from "date-fns"
import "./AdminRequests.css" // Ensure this file exists

const AdminRequests = () => {
  const [requests, setRequests] = useState([
    {
      id: 3,
      type: "red",
      description: "qasimali619 has requested cancellation for Booking ID: 1.",
      booking: {
        id: 1,
        timestamp: null,
        amount: 0,
        paymentDeadline: null,
        status: null,
        user: null,
        flight: null,
      },
      forAdmin: "Yes",
      status: "Untreated",
      timestamp: "2025-04-29T13:08:07.789+00:00",
    },
  ])

  const getBorderColor = (type) => {
    switch (type.toLowerCase()) {
      case "red":
        return "request-card-red"
      case "green":
        return "request-card-green"
      case "blue":
        return "request-card-blue"
      case "yellow":
        return "request-card-yellow"
      default:
        return "request-card-default"
    }
  }

  const handleAccept = (id) => {
    setRequests(requests.map((r) => (r.id === id ? { ...r, status: "Accepted" } : r)))
    console.log(`Request ${id} accepted`)
  }

  const handleReject = (id) => {
    setRequests(requests.map((r) => (r.id === id ? { ...r, status: "Rejected" } : r)))
    console.log(`Request ${id} rejected`)
  }

  const formatTimestamp = (timestamp) => {
    try {
      return formatDistanceToNow(new Date(timestamp), { addSuffix: true })
    } catch (error) {
      return "Invalid date"
    }
  }

  return (
    <div className="request-container">
      <AdminNav />

      <div className="request-inner">
        <h1 className="request-heading">Admin Requests</h1>

        <div className="request-list">
          {requests.map((request) => (
            <div key={request.id} className={`request-card ${getBorderColor(request.type)}`}>
              <div className="request-card-content">
                <div className="request-content">
                  <div className="request-badges">
                    <span className="request-badge">ID: {request.id}</span>
                    <span
                      className={`request-status-badge ${
                        request.status === "Untreated"
                          ? "request-status-untreated"
                          : request.status === "Accepted"
                          ? "request-status-accepted"
                          : "request-status-rejected"
                      }`}
                    >
                      {request.status}
                    </span>
                  </div>
                  <p className="request-description">{request.description}</p>
                  <div className="request-timestamp">
                    <Clock size={16} style={{ marginRight: "4px" }} />
                    {formatTimestamp(request.timestamp)}
                  </div>
                </div>

                <div className="request-actions">
                  {request.status === "Untreated" ? (
                    <>
                      <button className="request-button request-accept" onClick={() => handleAccept(request.id)}>
                        <Check size={16} style={{ marginRight: "4px" }} />
                        Accept
                      </button>
                      <button className="request-button request-reject" onClick={() => handleReject(request.id)}>
                        <X size={16} style={{ marginRight: "4px" }} />
                        Reject
                      </button>
                    </>
                  ) : (
                    <span className="request-status-note">
                      Request has been {request.status.toLowerCase()}
                    </span>
                  )}
                </div>
              </div>
            </div>
          ))}

          {requests.length === 0 && (
            <div className="request-empty">
              <p>No requests found</p>
            </div>
          )}
        </div>
      </div>
    </div>
  )
}

export default AdminRequests
