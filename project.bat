@echo off

echo Starting Spring Boot Backend...
start cmd /k "cd /d %~dp0backend && .\mvnw spring-boot:run"

timeout /t 5 /nobreak > NUL

echo Starting Frontend (npm run dev)...
start cmd /k "cd /d %~dp0frontend && npm run dev"

exit
