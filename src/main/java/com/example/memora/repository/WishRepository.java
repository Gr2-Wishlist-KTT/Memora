package com.example.memora.repository;

import com.example.memora.model.Wish;
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

    private final RowMapper<Wish> rowMapper = (rs, rowNum) -> {
        Wish wish = new Wish();
        wish.setProductName(rs.getString("product_name"));
        wish.setId(rs.getInt("id"));
        wish.setPrice(rs.getDouble("price"));
        wish.setDescription(rs.getString("description"));
        wish.setLinkToProduct(rs.getString("link"));
        wish.setQuantity(rs.getInt("quantity"));

        return wish;
    };


    // Metode for at kunne retunere ønsker
    public List<Wish> getWishes(int id) {
        String sql = """
                SELECT Wish.id, Wish.product_name, Wish.link, Wish.description, Wish.quantity, Wish.price
                FROM Wish
                WHERE Wishlist_id = ?;
                """;
        return jdbcTemplate.query(sql, rowMapper, id);
    }



    public Wish findWish(int id) {
        String sql = """
                SELECT id, product_name,link, description, quantity, price
                FROM Wish
                WHERE id = ?;
              
                """;
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    // Metode for at kunne tilføje ønsker
    public int saveWishes(Wish wish, int wishlistId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO Wish (product_name, description, wishlist_id, link, price, quantity) VALUES (?,?,?,?,?,?)";


        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, wish.getProductName());
            ps.setString(2, wish.getDescription());
            ps.setInt(3, wishlistId);
            ps.setString(4, wish.getLinkToProduct());
            ps.setDouble(5, wish.getPrice());
            ps.setInt(6, wish.getQuantity());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void removeWish(int wishId) {
        String sql = "DELETE FROM Wish where id = ?";
        jdbcTemplate.update(sql, wishId);
    }

    public void updateWish(Wish wish) {
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