import './App.css'
import Home from './pages/Home'
import { Route, Routes } from 'react-router-dom'
import Login from './pages/Login'
import Packages from './pages/Packages'
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

function App() {

  return (
    <>
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/login' element={<Login/>}/>
        <Route path='/packages' element={<Packages/>}/>
        <Route path='/login/user' element={<FlightSearchForm/>}/>
        <Route path='/login/admin' element={<AdminHome/>}/>
        <Route path='/admin/Flights' element={<AdminFlights/>}/>
        <Route path='/admin/Add-Flights' element={<AdminAddFlights/>}/>
        <Route path='/search-results' element={<SearchResultsPage/>}/>
        <Route path='/passenger-details' element={<PassengerDetailsPage/>}/>
        <Route path="/booking-details" element={<BookingDetailsPage />} />
        <Route path='/booking-history' element={<BookingHistoryPage />} />
        <Route path='/admin/Requests' element={<AdminRequests/>}/>
        <Route path='/admin/Reviews' element={<AdminReviews/>}/>
        <Route path='/notifications' element={<NotificationsPage />} />
      </Routes>
    </>
  )
}

export default App
