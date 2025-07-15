✈️ Airline-Reservation-System (SkyNest)

SkyNest is a full-featured Airline Reservation System designed for both passengers and administrators.
It allows users to book flights, manage their tickets, and receive notifications.
Admins can manage flights, aircraft, and monitor real-time statistics through a secure dashboard.

🧩 Modules

👤 User Module

Flight booking and history

View and download tickets

Real-time notifications on request approvals

Submit feedback on flights

🛠️ Admin Module

AI-powered review analyzer using Flask + TextBlob

View meaningful reviews (only very good or very bad sentiment)

Create flights and assign aircraft

Approve or reject booking requests

Monitor aircraft status

Detailed statistics on dashboard (see below)

🔒 Security Features (New)

✅ Spring Boot Security integrated

✅ JWT Authentication with refresh tokens

✅ Tokens handled through HttpOnly cookies for enhanced security

✅ Session timeout set to 1 hour (auto logout on inactivity)

✅ Role-based login (ADMIN and USER)

✅ Frontend protected routes — no API can be accessed without logging in

⚙️ Tech Stack

🔙 Backend

Spring Boot

Spring Security + JWT (with refresh token)

JDBC Template

Spring Data JPA

MySQL Workbench

Flask

TextBlob

🎨 Frontend

React

Recharts

CSS

📊 Admin Dashboard Stats

👥 Total Passengers

🛫 Total Flights

💰 Total Revenue

📈 Occupancy Rate per Airline (Bar Chart)

✅ Flight Status Summary (Pie Chart)

🚀 How to Run the Project

Install prerequisites:

Java & Maven

MySQL

Node.js & npm

Python (for Flask + TextBlob)

Backend Setup:

Update application.properties with your database credentials.

Run the backend once to generate schema.

Execute triggers.sql for database triggers.

Frontend Setup:

cd into the frontend folder.

Run npm install then npm run dev.

Start everything:

Use the provided project.bat script in the root directory to start backend, frontend, and Flask services.

🔐 Authentication & Tokens

All endpoints are secured with Spring Security.

JWT tokens are issued and stored in HttpOnly cookies to prevent XSS token theft.

Refresh token flow allows seamless session renewal.

Idle sessions expire after 1 hour.

Role-based access controls features for ADMIN and USER.

Frontend routes are protected — API calls without valid tokens are blocked.

🧪 Demo

📹 Project Demo Drive Link

[Click to Watch](https://drive.google.com/file/d/1MVi0IEjsZS1H612lfKXk4xSzRpq28jEE/view?usp=drive_link)

📬 Feedback & Contributions

Feel free to open issues or submit pull requests for improvements!
