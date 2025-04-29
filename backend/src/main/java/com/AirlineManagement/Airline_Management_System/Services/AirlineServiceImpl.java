package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.Airline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Service
public class AirlineServiceImpl implements AirlineService{
    @Autowired
    private JdbcTemplate template;
    @Override
    public List<Airline> getAll() {
        String sql = "SELECT * FROM Airline";
        return template.query(sql, new BeanPropertyRowMapper<>(Airline.class));
    }

    @Override
    public Airline get(Long id) {
        String sql = "SELECT * FROM Airline WHERE id = ?";
        return template.queryForObject(sql, new Object[]{id},new BeanPropertyRowMapper<>(Airline.class));
    }

    @Override
    public Airline create(Airline airline) {
        String sql = "INSERT INTO airlines (name) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, airline.getName());
            return ps;
        }, keyHolder);

        airline.setId(keyHolder.getKey().longValue());
        return airline;
    }
}
