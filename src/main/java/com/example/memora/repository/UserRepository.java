package com.example.memora.repository;

import com.example.memora.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> rowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUserName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));

        return user;
    };

    public User findUserById(String email){
        String sql = """
                SELECT id, username, password, email
                FROM user
                WHERE email = ?
                """;

        return jdbcTemplate.queryForObject(sql, rowMapper, email);
    }

    public void saveUser(User user) {
        String sql = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, user.getUserName(), user.getPassword(), user.getEmail());


    }


}
