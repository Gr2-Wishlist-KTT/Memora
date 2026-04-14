package com.example.memora.repository;

import com.example.memora.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class WishlistRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // Lambda Udtryk - rowMapper
    private final RowMapper<Wishlist> rowMapper = (rs, rowNum) -> {
        Wishlist wishList = new Wishlist();
        wishList.setId(rs.getInt("id"));
        wishList.setOwner(rs.getInt("owner"));
        wishList.setTitle(rs.getString("title"));


        return wishList;
    };

    public void createWishlist(Wishlist wishlist, int ownerId) {
        String sql = "INSERT INTO Wishlist (title, owner) VALUES (?, ?)";

        jdbcTemplate.update(sql, wishlist.getTitle(), wishlist.getOwner());
    }


    // Metode for at kunne retunere ønsker
    public List<Wishlist> getWishLists(int owner) {
        String sql = """
                SELECT Wishlist.id, Wishlist.title, Wishlist.owner
                FROM Wishlist
                WHERE Wishlist.owner = ?;
                """;
        return jdbcTemplate.query(sql, rowMapper, owner);

    }

    public Wishlist getWishList(int wishlistID) {
        String sql = """
                SELECT Wishlist.id, Wishlist.title, Wishlist.owner
                FROM Wishlist
                WHERE Wishlist.owner = ?;
                """;
        return jdbcTemplate.queryForObject(sql, rowMapper, wishlistID);
    }

    public void updateWishlist(int id, Wishlist wishlist) {
        String sql = "UPDATE Wishlist SET title = ? WHERE id = ?";
        jdbcTemplate.update(sql, wishlist.getTitle(), id);
    }
}

