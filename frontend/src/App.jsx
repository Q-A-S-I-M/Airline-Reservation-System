import { useState } from 'react'
import './App.css'
import Home from './pages/Home'
import { Route, Routes } from 'react-router-dom'
import Login from './pages/Login'
import Packages from './pages/Packages'
import AdminHome from './pages/AdminHome'
import AdminFlights from './pages/AdminFlights'
import AdminAddFlights from './pages/AdminAddFlights'
import FlightSearchForm from './pages/FlightSearchForm'
import SearchResultsPage from './components/SearchResultsPage'
import AdminRequests from './pages/AdminRequests'
import AdminReviews from './pages/AdminReviews'

function App() {
  const [count, setCount] = useState(0)

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
        <Route path='/admin/Requests' element={<AdminRequests/>}/>
        <Route path='/admin/Reviews' element={<AdminReviews/>}/>
      
      </Routes>
    </>
  )
}

export default App
