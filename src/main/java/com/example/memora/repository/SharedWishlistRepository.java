package com.example.memora.repository;

import com.example.memora.model.SharedWishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SharedWishlistRepository {

    private final JdbcTemplate jdbcTemplate;

    public SharedWishlistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<SharedWishlist> rowMapper = (rs, rowNum) -> {
        SharedWishlist sharedWishlist = new SharedWishlist();
        sharedWishlist.setId(rs.getInt("id"));
        sharedWishlist.setWishlistId(rs.getInt("wishlist_id"));
        sharedWishlist.setSharedWithUserId(rs.getInt("shared_with_user_id"));

        return sharedWishlist;
    };

    public List<SharedWishlist> findSharesForUser(int userId) {
        String sql = """
                    SELECT * FROM shared_wishlist
                    WHERE shared_with_user_id = ?
                """;

        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    public void addShare(int wishlistId, int userId) {
        String sql = """
                    INSERT INTO shared_wishlist (wishlist_id, shared_with_user_id)
                    VALUES (?, ?)
                """;

        jdbcTemplate.update(sql, wishlistId, userId);
    }
}
