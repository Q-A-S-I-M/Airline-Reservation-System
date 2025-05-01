import './App.css'
import { Route, Routes } from 'react-router-dom'
import Login from './pages/Login'
import AdminHome from './pages/AdminHome'
import AdminFlights from './pages/AdminFlights'
import AdminAddFlights from './pages/AdminAddFlights'
import FlightSearchForm from './pages/FlightSearchForm'
import SearchResultsPage from './pages/SearchResultsPage'
import AdminRequests from './pages/AdminRequests'
import AdminReviews from './pages/AdminReviews'
import PassengerDetailsPage from './pages/PassengerDetailsPage'
import BookingDetailsPage from './pages/BookingDetailsPage.jsx';
import BookingHistoryPage from './pages/BookingHistoryPage.jsx';
import NotificationsPage from './pages/NotificationsPage.jsx';
import PaymentSuccessPage from './pages/PaymentSuccessPage.jsx';
import CancelSuccessPage from './pages/CancelSuccessPage.jsx';
import ViewTicketsPage from './pages/ViewTicketsPage.jsx';
import FeedbackPage from './pages/FeedbackPage.jsx';
import FeedbackSuccessPage from './pages/FeedbackSuccessPage.jsx';



function App() {

  return (
    <>
      <Routes>
        <Route path='/' element={<Login/>}/>
        <Route path='/login/admin' element={<AdminHome/>}/>
        <Route path='/admin/Flights' element={<AdminFlights/>}/>
        <Route path='/admin/Add-Flights' element={<AdminAddFlights/>}/>
        <Route path='/admin/Requests' element={<AdminRequests/>}/>
        <Route path='/admin/Reviews' element={<AdminReviews/>}/>
        <Route path='/notifications' element={<NotificationsPage />} />

        <Route path='/login/user' element={<FlightSearchForm/>}/>
        <Route path='/search-results' element={<SearchResultsPage/>}/>
        <Route path='/passenger-details' element={<PassengerDetailsPage/>}/>
        <Route path="/booking-details" element={<BookingDetailsPage />} />
        <Route path='/booking-history' element={<BookingHistoryPage />} />
        <Route path='/payment-success' element={<PaymentSuccessPage />} />
        <Route path='/cancel-success' element={<CancelSuccessPage />} />
        <Route path='/view-tickets' element={<ViewTicketsPage />} />
        <Route path='/feedback' element={<FeedbackPage />} />
        <Route path='/feedback-success' element={<FeedbackSuccessPage />} />
      </Routes>
    </>
  )
}

export default App
