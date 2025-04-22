package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.CustomMappers.FlightRowMapper;
import com.AirlineManagement.Airline_Management_System.Entities.Feedback;
import com.AirlineManagement.Airline_Management_System.Entities.Flight;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FeedbackServiceImpl implements FeedbackService{
     private final RestTemplate restTemplate = new RestTemplate();
     @Autowired
     private JdbcTemplate template;
    @Override
    public Feedback submit(Feedback feedback) {
         String url = "http://localhost:5001/analyze";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("comment", feedback.getComments());

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        int aiRating =(int) response.getBody().get("sentiment");
        feedback.setRating(aiRating);
        feedback.setTimestamp(time());
        String sql = "INSERT INTO Feedbacks (comments, rating, timestamp, flight_id, username) VALUES (?, ?, ?, ?, ?)";
        template.update(sql, feedback.getComments(), feedback.getRating(), feedback.getTimestamp(), feedback.getFlight().getId(), feedback.getUser().getUsername());
        return feedback;
    }
    public Date time(){
        LocalDateTime currentTime = LocalDateTime.now();
        Date date = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
        return date;
    }

    @Override
    public List<Flight> get() {
        String sql = "SELECT f.id, f.arrival, f.departure, f.to_location, f.from_location, f.price, f.booked_seats, f.total_seats, f.duration, f.status, a.id, a.name, ac.id, ac.model, ac.status, ac.seats FROM flights f JOIN airlines a ON f.airline_id = a.id JOIN aircrafts ac ON f.aircraft_id = ac.id WHERE f.id IN (SELECT f.id FROM feedbacks fd JOIN flights f ON fd.flight_id = f.id GROUP BY fd.flight_id HAVING AVG(fd.rating) < -0.5 OR AVG(fd.rating) > 0.5) ORDER BY f.id DESC;";
        List<Flight> feedbackList = template.query(sql, new FlightRowMapper());
        return feedbackList;
    }
    @Override
    public List<Feedback> getFeedbacks(Long id) {
        String sql = "SELECT * FROM Feedbacks WHERE flight_id = ? ORDER BY timestamp DESC";
        return template.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Feedback.class));
    }
}
