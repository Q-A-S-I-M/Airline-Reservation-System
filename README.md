# ✈️ Airline-Reservation-System (SkyNest)

**SkyNest** is a full-featured Airline Reservation System designed for both passengers and administrators. It allows users to book flights, manage their tickets, and receive notifications. Admins can manage flights, aircrafts, and monitor real-time statistics through dashboard.

---

## 🧩 Modules

### 👤 User Module
- Flight booking and history
- View and download tickets
- Real-time notifications on request approvals
- Submit feedback on flights
- Chatbot assistance integrated via third-party script

### 🛠️ Admin Module
- AI-powered review analyzer using Flask + TextBlob
- View meaningful reviews (only very good or very bad sentiment)
- Create flights and assign aircraft
- Approve or reject booking requests
- Monitor aircraft status
- Detailed statistics on dashboard (see below)

### 🔄 Other Features
- Auto seat assignment and management
- Smart notification system
- Trigger-based updates in database
- Sentiment analysis API integration via Flask
- Secure and efficient backend operations

---

## ⚙️ Tech Stack

### 🔙 Backend
- Spring Boot
- JDBC Template
- Spring Data JPA
- MySQL Workbench
- Flask
- TextBlob

### 🎨 Frontend
- React
- Recharts
- CSS

---

## 📊 Admin Dashboard Stats

- 👥 Total Passengers
- 🛫 Total Flights
- 💰 Total Revenue
- 📈 Occupancy Rate per Airline (Bar Chart)
- ✅ Flight Status Summary (Pie Chart)

---

## 🚀 How to Run the Project

1. Ensure the following are installed:
   - Python (with Flask and TextBlob)
   - Node.js and npm
   - MySQL
   - Java and Maven

2. **Backend Setup**
   - Open `application.properties` in the backend folder.
   - Configure your **database URL, username, and password** according to your local MySQL setup.

3. **Schema & Triggers**
   - Run the backend application **once** to allow Spring Boot to generate the schema.
   - Then execute the `triggers.sql` file to add database triggers.

4. **Start the Project**
   - Use the `project.bat` file in the `Airline-Reservation-System` directory to launch all services.

---

## 🔐 Authentication

The system includes login and registration functionality, though the implementation type (JWT/session-based) is not specified.

---

## 🧪 Demo

📹 **Project Demo Drive Link**  
[Click to Watch](https://drive.google.com/drive/u/1/folders/15goo0FVQtQImLoFby4wLPcgTFdkjYqCi)

---

## 📬 Feedback & Contributions

Feel free to suggest improvements or report issues via pull requests or issues if this project is hosted on GitHub.

---
