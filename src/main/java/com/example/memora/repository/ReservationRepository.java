package com.example.memora.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isReserved(int wishId) {
        String sql = "SELECT COUNT(*) FROM Wish_reservation WHERE wish_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, wishId);
        return count > 0;
    }

    public int getReservedBy(int wishId) {
        String sql = "SELECT reserved_by_user_id FROM Wish_reservation WHERE wish_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, wishId);
    }

    public void reserve(int wishId, int userId) {
        String sql = "INSERT INTO Wish_reservation (wish_id, reserved_by_user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, wishId, userId);
    }

    public void unreserve(int wishId) {
        String sql = "DELETE FROM Wish_reservation WHERE wish_id = ?";
        jdbcTemplate.update(sql, wishId);
    }
}