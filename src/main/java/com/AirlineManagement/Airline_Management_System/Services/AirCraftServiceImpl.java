package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.AirCraft;
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
public class AirCraftServiceImpl implements AirCraftService {
    @Autowired
    private JdbcTemplate template;
    @Override
    public List<AirCraft> getAll() {
        String sql = "SELECT * FROM aircrafts";
        return template.query(sql, new BeanPropertyRowMapper<>(AirCraft.class));
    }

    @Override
    public AirCraft get(Long id) {
        String sql = "SELECT * FROM aircrafts WHERE id = "+id;
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(AirCraft.class));
    }

    @Override
    public AirCraft create(AirCraft airCraft) {
        String sql = "INSERT INTO aircrafts (model, seats, status) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, airCraft.getModel());
            ps.setInt(2, airCraft.getSeats());
            ps.setString(3, airCraft.getStatus());
            return ps;
        }, keyHolder);

        airCraft.setId(keyHolder.getKey().longValue());
        return airCraft;
    }




    @Override
    public AirCraft update(Long id, AirCraft updated) {
        String sql = "UPDATE aircrafts SET model = '" + updated.getModel() + "', seats = "
                + updated.getSeats() + ", status = '" + updated.getStatus() + "' WHERE id = " + id;
        int rows = template.update(sql);
        if (rows > 0) {
            updated.setId(id);
            return updated;
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM aircrafts WHERE id = "+id;
        int rowsAffected = template.update(sql);

        if (rowsAffected == 0) {
            throw new RuntimeException("Airline not found with id: " + id);
        }
    }
}
