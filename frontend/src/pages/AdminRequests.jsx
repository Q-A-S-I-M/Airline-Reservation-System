import { useState, useEffect } from "react"
import api from '../api/axios'
import AdminNav from "../components/AdminNav"
import { Check, X, Clock } from "lucide-react"
import { formatDistanceToNow } from "date-fns"
import "./AdminRequests.css"

const AdminRequests = () => {
  const [requests, setRequests] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchRequests = async () => {
      try {
        const response = await api.get("http://localhost:8080/notifications/admin")
        setRequests(response.data)
      } catch (err) {
        console.error("❌ Error fetching requests:", err)
        setError("Failed to load requests")
      } finally {
        setLoading(false)
      }
    }

    fetchRequests()
  }, [])

  const getBorderColor = (type) => {
    switch (type?.toLowerCase()) {
      case "red":
        return "request-card-red"
      case "blue":
        return "request-card-blue"
      default:
        return "request-card-default"
    }
  }

  const sendRequest = async (url, data) => {
    try {
      const response = await api.post(url, data)
      console.log(`✅ POST to ${url}:`, response.data)
    } catch (error) {
      console.error(`❌ Error POST to ${url}:`, error.response?.data || error.message)
    }
  }

  const handleAccept = (request) => {
    const { id, type, booking } = request
    const url =
      type === "Red"
        ? "http://localhost:8080/notifications/admin/cancellation-accept"
        : type === "Blue"
        ? "http://localhost:8080/notifications/admin/booking-accept"
        : ""

    if (url && booking?.id) {
      sendRequest(url, { id: booking.id })
      updateStatus(id, "Accepted")
    }
  }

  const handleReject = (request) => {
    const { id, type, booking } = request
    const url =
      type === "Red"
        ? "http://localhost:8080/notifications/admin/cancellation-reject"
        : type === "Blue"
        ? "http://localhost:8080/notifications/admin/booking-reject"
        : ""

    if (url && booking?.id) {
      sendRequest(url, { id: booking.id })
      updateStatus(id, "Rejected")
    }
  }

  const updateStatus = (id, newStatus) => {
    setRequests((prev) =>
      prev.map((r) => (r.id === id ? { ...r, status: newStatus } : r))
    )
  }

  const formatTimestamp = (timestamp) => {
    try {
      return formatDistanceToNow(new Date(timestamp), { addSuffix: true })
    } catch {
      return "Invalid date"
    }
  }

  return (
    <>
      <AdminNav />
      <div className="request-container">
        <div className="request-inner">
          <h1 className="request-heading">Admin Requests</h1>

          {loading ? (
            <p>Loading requests...</p>
          ) : error ? (
            <p style={{ color: "red" }}>{error}</p>
          ) : requests.length === 0 ? (
            <div className="request-empty">
              <p>No requests found</p>
            </div>
          ) : (
            <div className="request-list">
              {requests.map((request) => (
                <div
                  key={request.id}
                  className={`request-card ${getBorderColor(request.type)}`}
                >
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
                          <button
                            className="request-button request-accept"
                            onClick={() => handleAccept(request)}
                          >
                            <Check size={16} style={{ marginRight: "4px" }} />
                            Accept
                          </button>
                          <button
                            className="request-button request-reject"
                            onClick={() => handleReject(request)}
                          >
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
            </div>
          )}
        </div>
      </div>
    </>
  )
}

export default AdminRequests