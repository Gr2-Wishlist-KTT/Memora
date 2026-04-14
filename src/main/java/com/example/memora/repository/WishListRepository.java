package com.example.memora.repository;

import com.example.memora.model.WishList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class WishListRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // Lambda Udtryk - rowMapper
    private final RowMapper<WishList> rowMapper = (rs, rowNum) -> {
        WishList wishList = new WishList();
        wishList.setId(rs.getInt("id"));
        wishList.setOwner(rs.getInt("owner"));
        wishList.setTitle(rs.getString("title"));


        return wishList;
    };

    public int createWishlist(String title, int ownerId) {
        String sql = "INSERT INTO Wishlist (title, owner) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, title);
            ps.setInt(2, ownerId);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }


    // Metode for at kunne retunere ønsker
    public List<WishList> getWishLists(int owner) {
        String sql = """
                SELECT Wishlist.id, Wishlist.title, Wishlist.owner
                FROM Wishlist
                WHERE Wishlist.owner = ?;
                """;
        return jdbcTemplate.query(sql, rowMapper, owner);

    }
    public void updateWishlist(int id, String title) {
        String sql = "UPDATE Wishlist SET title = ? WHERE id = ?";
        jdbcTemplate.update(sql, title, id);
    }
}

