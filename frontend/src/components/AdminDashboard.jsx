import React, { useEffect, useState } from 'react';
import api from '../api/axios'
import './AdminDashboard.css';
import AOS from 'aos';
import 'aos/dist/aos.css';
import {
  BarChart, Bar, XAxis, YAxis, Tooltip, PieChart, Pie, Cell, Legend, ResponsiveContainer,
} from 'recharts';

const dummyData = {
  passengers: 6,
  flights: 5,
  revenue: 15600,
  occupany_rate: [
    { airline: "SkyFly Airways", rate: 10 },
    { airline: "Global Wings", rate: 150 },
    { airline: "CloudJet Airlines", rate: 140 },
    { airline: "AeroNova", rate: 80 },
    { airline: "BlueHorizon Air", rate: 20 }
  ],
  status_summary: [
    { status: "Landed", count: 4 },
    { status: "Departed", count: 1 }
  ]
};

const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

function AdminDashboard() {
  const [data, setData] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        const response = await api.get('http://localhost:8080/admin/dashboard');
        setData(response.data);
      } catch (err) {
        console.error('Failed to fetch dashboard data:', err);
        setError('Failed to fetch data, using fallback values.');
        setData(dummyData); // Fallback to dummy data if API fails
      }
    };

    fetchDashboardData();
  }, []);
  useEffect(() => {
    AOS.init({ duration: 1000 });
  }, []);

  if (!data) return <div>Loading...</div>;

  return (
    <div className="dashboard-container">
      <h1 className="dashboard-title">Admin Dashboard</h1>
      {error && <p style={{ color: 'red', textAlign: 'center' }}>{error}</p>}

      <div className="dashboard-cards-container">
        <div className="dashboard-card " data-aos="flip-left">
          <h2>Passengers</h2>
          <p>{data.passengers}</p>
        </div>
        <div className="dashboard-card" data-aos="flip-left">
          <h2>Flights</h2>
          <p>{data.flights}</p>
        </div>
        <div className="dashboard-card" data-aos="flip-left">
          <h2>Revenue</h2>
          <p>${data.revenue}</p>
        </div>
      </div>

      <div className="dashboard-charts-container">
        <div className="dashboard-chart-box" data-aos='zoom-in'>
          <h3>Occupancy Rate</h3>
          <ResponsiveContainer width="100%" height={300}>
            <BarChart data={data.occupany_rate}>
              <XAxis dataKey="airline" />
              <YAxis />
              <Tooltip />
              <Bar dataKey="rate" fill="#8884d8" />
            </BarChart>
          </ResponsiveContainer>
        </div>

        <div className="dashboard-chart-box" data-aos='zoom-in'>
          <h3>Flight Status Summary</h3>
          <ResponsiveContainer width="100%" height={300}>
            <PieChart>
              <Pie
                data={data.status_summary}
                dataKey="count"
                nameKey="status"
                cx="50%"
                cy="50%"
                outerRadius={100}
                label
              >
                {data.status_summary.map((entry, index) => (
                  <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                ))}
              </Pie>
              <Legend />
              <Tooltip />
            </PieChart>
          </ResponsiveContainer>
        </div>
      </div>
    </div>
  );
}

export default AdminDashboard;
