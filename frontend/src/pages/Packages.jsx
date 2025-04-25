import React from 'react'
import HomeNav from '../components/HomeNav'
import './Packages.css'

const packages = [
  {
    "package_id": "PKG001",
    "name": "Economy Saver",
    "description": "Affordable travel package with essential amenities.",
    "class": "Economy",
    "price": 299.99,
    "features": {
      "baggage_allowance": "15kg",
      "meals": "Complimentary snacks",
      "cancellation_policy": "Non-refundable",
      "priority_check_in": false,
      "in_flight_entertainment": false
    }
  },
  {
    "package_id": "PKG002",
    "name": "Business Comfort",
    "description": "Business-class experience with extra comfort.",
    "class": "Business",
    "price": 799.99,
    "features": {
      "baggage_allowance": "30kg",
      "meals": "Gourmet meals included",
      "cancellation_policy": "Partial refund available",
      "priority_check_in": true,
      "in_flight_entertainment": true
    }
  },
  {
    "package_id": "PKG003",
    "name": "Luxury First-Class",
    "description": "Premium first-class experience with VIP treatment.",
    "class": "First-Class",
    "price": 1499.99,
    "features": {
      "baggage_allowance": "50kg",
      "meals": "Five-course meal & champagne",
      "cancellation_policy": "Fully refundable",
      "priority_check_in": true,
      "in_flight_entertainment": true,
      "private_suite": true
    }
  },
  {
    "package_id": "PKG004",
    "name": "Family Getaway",
    "description": "Special package for families with added benefits.",
    "class": "Economy",
    "price": 899.99,
    "features": {
      "baggage_allowance": "25kg per person",
      "meals": "Kid-friendly meals available",
      "cancellation_policy": "Flexible cancellation",
      "priority_check_in": false,
      "in_flight_entertainment": true,
      "extra_legroom": true
    }
  },
  {
    "package_id": "PKG005",
    "name": "Honeymoon Special",
    "description": "Romantic getaway with exclusive perks.",
    "class": "Business",
    "price": 1299.99,
    "features": {
      "baggage_allowance": "35kg",
      "meals": "Customized candlelight dinner",
      "cancellation_policy": "Refundable with conditions",
      "priority_check_in": true,
      "in_flight_entertainment": true,
      "lounge_access": true
    }
  },
  {
    "package_id": "PKG006",
    "name": "Adventure Explorer",
    "description": "Perfect for adventure seekers traveling with gear.",
    "class": "Economy",
    "price": 649.99,
    "features": {
      "baggage_allowance": "20kg",
      "meals": "High-energy meals included",
      "cancellation_policy": "Partial refund",
      "priority_check_in": false,
      "in_flight_entertainment": false,
      "extra_legroom": true
    }
  },
  {
    "package_id": "PKG007",
    "name": "Corporate Express",
    "description": "Designed for business travelers needing efficiency.",
    "class": "Business",
    "price": 1100.00,
    "features": {
      "baggage_allowance": "40kg",
      "meals": "Premium business meals",
      "cancellation_policy": "Fully refundable",
      "priority_check_in": true,
      "in_flight_entertainment": true,
      "lounge_access": true,
      "wifi_access": true
    }
  },
  {
    "package_id": "PKG008",
    "name": "Student Special",
    "description": "Budget-friendly travel for students.",
    "class": "Economy",
    "price": 199.99,
    "features": {
      "baggage_allowance": "10kg",
      "meals": "Snack box included",
      "cancellation_policy": "Non-refundable",
      "priority_check_in": false,
      "in_flight_entertainment": false
    }
  },
  {
    "package_id": "PKG009",
    "name": "Senior Citizen Relax",
    "description": "Comfortable travel package for seniors.",
    "class": "Economy",
    "price": 350.00,
    "features": {
      "baggage_allowance": "25kg",
      "meals": "Soft diet meals included",
      "cancellation_policy": "Partial refund available",
      "priority_check_in": true,
      "extra_legroom": true
    }
  },
  {
    "package_id": "PKG010",
    "name": "First-Class Elite",
    "description": "Ultra-luxury experience for high-end travelers.",
    "class": "First-Class",
    "price": 2999.99,
    "features": {
      "baggage_allowance": "Unlimited",
      "meals": "Michelin-star meals",
      "cancellation_policy": "Fully refundable",
      "priority_check_in": true,
      "in_flight_entertainment": true,
      "private_suite": true,
      "chauffeur_service": true
    }
  },
  {
    "package_id": "PKG011",
    "name": "Weekend Getaway",
    "description": "Short trip package for weekend travelers.",
    "class": "Economy",
    "price": 249.99,
    "features": {
      "baggage_allowance": "10kg",
      "meals": "Light refreshments",
      "cancellation_policy": "Non-refundable",
      "priority_check_in": false
    }
  },
  {
    "package_id": "PKG012",
    "name": "Luxury Jetsetter",
    "description": "Exclusive experience for global travelers.",
    "class": "First-Class",
    "price": 3999.99,
    "features": {
      "baggage_allowance": "Unlimited",
      "meals": "Customized menu",
      "cancellation_policy": "Fully refundable",
      "priority_check_in": true,
      "in_flight_entertainment": true,
      "private_suite": true,
      "concierge_service": true
    }
  },
  {
    "package_id": "PKG013",
    "name": "Medical Emergency Travel",
    "description": "Priority travel for medical emergencies.",
    "class": "Business",
    "price": 1250.00,
    "features": {
      "baggage_allowance": "40kg",
      "meals": "Health-conscious meals",
      "cancellation_policy": "Flexible cancellation",
      "priority_check_in": true,
      "medical_assistance": true
    }
  }
];

const Packages = () => {
  return (
    <div>
      <HomeNav/>
      <div className="package_heading">
      <h1>Airline Packages</h1>
      </div>
      

      <div className="container">
      {packages.map((pkg) => (
        <div key={pkg.package_id} className="cards">
          <h2 className="cards-header">{pkg.name}</h2>
          <div className="cards-body">
            <p className="description">{pkg.description}</p>
            <p><strong>Class:</strong> {pkg.class}</p>
            <p className="price">${pkg.price}</p>
            <div className="features">
              <h3>Features:</h3>
              <ul>
                <li>Baggage: {pkg.features.baggage_allowance}</li>
                <li>Meals: {pkg.features.meals}</li>
                <li>Cancellation: {pkg.features.cancellation_policy}</li>
                {pkg.features.priority_check_in && <li>Priority Check-In</li>}
                {pkg.features.in_flight_entertainment && <li>In-flight Entertainment</li>}
                {pkg.features.private_suite && <li>Private Suite</li>}
              </ul>
            </div>
          </div>
        </div>
      ))}
    </div>
      
    </div>
  )
}

export default Packages
