import React from 'react'
import HomeNav from '../components/HomeNav'
import './Home.css'
import { FaCopyright } from "react-icons/fa6";
// import sampleVideo from "../assets/video1.mp4";
import AOS from 'aos';
import 'aos/dist/aos.css';
import { useEffect } from 'react';

const Home = () => {

  useEffect(() => {
    AOS.init({ duration: 1000 });
  }, []);

  return (
    <div>
      <HomeNav />

      <div className='HomeBody-container'>

        <div className="content">
          <div className="section1" data-aos="fade-right"></div>

          <div className="section2" data-aos="fade-up">
            <h1>Welcome to SkyNest Airline </h1> SkyNest is a modern airline management system designed to streamline
            operations, enhance customer experience, and optimize flight scheduling. Whether you're a passenger, an airline administrator, or a travel agent,
            our system ensures smooth and hassle-free airline operations.
          </div>
        </div>


        <div className="developers">

          <h1>Developed By</h1>

          <div className="box-container">

            <div className='developer-info' data-aos='zoom-in'>
              {/* <div className="box" id='box1'></div > */}
              <div className="info">
                <h2>Jahanzaib Mirza</h2>
              </div>
            </div>

            <div className='developer-info' data-aos='zoom-in'>
              {/* <div className="box" id='box2'>
              </div> */}
              <div className="info">
                <h2>Maaz Khan</h2>
              </div>
            </div>

            <div className='developer-info' data-aos='zoom-in'>
              {/* <div className="box" id='box3'>
              </div> */}
              <div className="info">
                <h2>Qasim Ali</h2>
              </div>
            </div>

          </div>

        </div>

      </div>


      <div className="HomeFooter-container">
          <div className="line1">
          <h2>Privacy Policy</h2> <h2>Terms of Services</h2>
          </div>
          <div className="line2">
            <h3><FaCopyright/></h3> <h2>2025</h2> <h2>All Rights Reserved</h2>
          </div>
        </div>


    </div>
  )
}

export default Home
