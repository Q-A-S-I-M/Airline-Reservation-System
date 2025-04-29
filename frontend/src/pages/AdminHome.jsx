import React, { useState } from 'react'
import AdminNav from '../components/AdminNav'
import './AdminHome.css'
import AOS from 'aos';
import 'aos/dist/aos.css';
import { useEffect } from 'react';

const AdminHome = () => {

const [PassengersCount, setBookingCount] = useState(150);
const [AirlinesCount, setAirlinesCount] = useState(200);
const [RevenueCount, setRevenueCount] = useState(454656456);
const [FlightsCount, setFlightsCount] = useState(56);

useEffect(() => {
    AOS.init({ duration: 1000 });
  }, []);

  return (
    <div>
      <AdminNav/>

      <div className="Dashboard-Container">

        <div className="Data-Cards">

        <div className="info-cards" data-aos="fade-right">
          <p>Passengers</p>
          <p>{PassengersCount}</p>
        </div>
        <div className="info-cards" data-aos="flip-left">
          <p>Airlines</p>
          <p>{AirlinesCount}</p>
        </div>
        <div className="info-cards" data-aos="flip-right">
          <p>Flights</p>
          <p>{FlightsCount}</p>
        </div>
        <div className="info-cards" data-aos="fade-left">
          <p>Revenue</p>
          <p>{RevenueCount} $</p>
        </div>

        </div>

        <div className="chart-statistics">
          <div className="chart1">
            <h1>DATA 1</h1>
          </div>
          <div className="chart2">
            <h1>DATA 2</h1>
          </div>
        </div>

      </div>


    </div>
  )
}

export default AdminHome
