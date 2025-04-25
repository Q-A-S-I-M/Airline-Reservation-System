@echo off
echo Starting Flask App...
start cmd /k "cd /d E:\Study Material\PROJECTS\DB\Semester Project\Airline-Reservation-System\backend\sentiment-analysis && python app.py"

timeout /t 5 /nobreak > NUL

echo Starting Spring Boot...
cd /d "E:\Study Material\PROJECTS\DB\Semester Project\Airline-Reservation-System\backend"
start cmd /k ".\mvnw spring-boot:run"

timeout /t 5 /nobreak > NUL

echo Starting Frontend (npm run dev)...
cd /d "E:\Study Material\PROJECTS\DB\Semester Project\Airline-Reservation-System\frontend"
start cmd /k "npm run dev"
