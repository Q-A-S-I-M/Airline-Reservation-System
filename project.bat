@echo off
echo Starting Flask App...
start cmd /k "cd /d E:\Study Material\PROJECTS\DB\Semester Project\Airline-Reservation-System\sentiment-analysis && python app.py"

timeout /t 5 /nobreak > NUL

echo Starting Spring Boot...
cd /d "E:\Study Material\PROJECTS\DB\Semester Project\Airline-Reservation-System"
start cmd /k ".\mvnw spring-boot:run"
