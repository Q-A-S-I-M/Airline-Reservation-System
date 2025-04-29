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
          <div className="section1" data-aos="fade-right">
          {/* <video width="640" height="360" controls>
  <source src={sampleVideo} type="video/mp4" />
</video> */}
          </div>

          <div className="section2" data-aos="fade-up">
            <h1>Welcome to SkyNest Airline </h1> SkyNest is a modern airline management system designed to streamline
            operations, enhance customer experience, and optimize flight scheduling. Whether you're a passenger, an airline administrator, or a travel agent,
            our system ensures smooth and hassle-free airline operations.
          </div>
        </div>


        <div className="logos">

          <h1>Airlines Logos</h1>

          <div className="logo-container">
            <div className="logo" id='logo1' data-aos="flip-left"></div>
            <div className="logo" id='logo2' data-aos="flip-left"></div>
            <div className="logo" id='logo3' data-aos="flip-left"></div>
            <div className="logo" id='logo4' data-aos="flip-left"></div>
          </div>

          <div className="logo-container">
            <div className="logo" id='logo5' data-aos="flip-right"></div>
            <div className="logo" id='logo6' data-aos="flip-right"></div>
            <div className="logo" id='logo7' data-aos="flip-right"></div>
            <div className="logo" id='logo8' data-aos="flip-right"></div>
          </div>

        </div>

        {/* <div className="services">

          <h1>Our Services</h1>


        </div> */}

        <div className="developers">

          <h1>Developers</h1>

          <div className="box-container">

            <div className='developer-info' data-aos='zoom-in'>
              <div className="box" id='box1'></div >
              <div className="info">
                <h2>LUBRICANT MIRZA</h2>
                <h2>ROLL NO: 23K-3011</h2>
              </div>
            </div>

            <div className='developer-info' data-aos='zoom-in'>
              <div className="box" id='box2'>
              </div>
              <div className="info">
                <h2>MAAZ BEGUM</h2>
                <h2>ROLL NO: 23K-3036</h2>
              </div>
            </div>

            <div className='developer-info' data-aos='zoom-in'>
              <div className="box" id='box3'>
              </div>
              <div className="info">
                <h2>QASIM QURESHI</h2>
                <h2>ROLL NO: 23K-3002</h2>
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
