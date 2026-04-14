package com.example.memora.repository;

import com.example.memora.model.WishList;
import com.example.memora.model.Wishes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class WishRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Wishes> rowMapper = (rs, rowNum) -> {
        Wishes wishes = new Wishes();
        wishes.setProductName(rs.getString("product_name"));
        wishes.setId(rs.getInt("id"));
        wishes.setPrice(rs.getDouble("price"));
        wishes.setDescription(rs.getString("description"));
        wishes.setLinkToProduct(rs.getString("link"));
        wishes.setQuantity(rs.getInt("quantity"));

        return wishes;
    };


    // Metode for at kunne retunere ønsker
    public List<Wishes> getWishes(int id) {
        String sql = """
                SELECT Wish.id, Wish.product_name, Wish.link, Wish.description, Wish.quantity, Wish.price
                FROM Wish
                WHERE Wishlist_id = ?;
                """;
        return jdbcTemplate.query(sql, rowMapper, id);
    }

    // Metode for at kunne tilføje ønsker
    public int saveWishes(Wishes wishes, int wishlistId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO Wish (product_name, description, wishlist_id, link, price, quantity) VALUES (?,?,?,?,?,?)";


        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, wishes.getProductName());
            ps.setString(2, wishes.getDescription());
            ps.setInt(3, wishlistId);
            ps.setString(4, wishes.getLinkToProduct());
            ps.setDouble(5, wishes.getPrice());
            ps.setInt(6, wishes.getQuantity());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void removeWish(int wishId) {
        String sql = "DELETE FROM Wish where id = ?";
        jdbcTemplate.update(sql, wishId);
    }

    public void updateWish(Wishes wish) {
        String sql = """
                    UPDATE Wish
                    SET product_name = ?,
                        description = ?,
                        link = ?,
                        price = ?,
                        quantity = ?
                    WHERE id = ?
                """;

        jdbcTemplate.update(sql,
                wish.getProductName(),
                wish.getDescription(),
                wish.getLinkToProduct(),
                wish.getPrice(),
                wish.getQuantity(),
                wish.getId()
        );
    }
}