package com.example.memora.repository;

import com.example.memora.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> rowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));

        return user;
    };

    public User findUserByEmail(String email){
        String sql = """
                SELECT id, username, password, email
                FROM Profile
                WHERE email = ?
                """;

        return jdbcTemplate.queryForObject(sql, rowMapper, email);
    }

    public void saveUser(User user) {
        String sql = "INSERT INTO Profile (username, password, email) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail());

    }
}