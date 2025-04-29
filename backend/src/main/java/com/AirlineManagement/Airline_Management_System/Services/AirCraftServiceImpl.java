package com.AirlineManagement.Airline_Management_System.Services;

import com.AirlineManagement.Airline_Management_System.Entities.AirCraft;
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
        String sql = "SELECT * FROM aircrafts WHERE id = ?";
        return template.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(AirCraft.class));
    }

    @Override
    public AirCraft create(AirCraft airCraft) {
        String sql = "INSERT INTO aircrafts (model, seats, status) VALUES (?, ?, 'Unassigned')";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, airCraft.getModel());
            ps.setInt(2, airCraft.getSeats());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            long id = generatedId.longValue();
            String newSql = "INSERT INTO Seats (seat_no, status, aircraft_id) VALUES (?, 'Available', ?)";
            for (int i = 1; i <= airCraft.getSeats(); i++) {
                String seatNo = airCraft.getModel() + "-" + id + "-" + i;
                template.update(newSql, seatNo, id);
            }

            airCraft.setId(id);
            return airCraft;
        } else {
            return null;
        }
    }
}
