package com.example.memora.repository;

import com.example.memora.model.User;
import com.example.memora.model.Wish;
import com.example.memora.model.Wishlist;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;


@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    // FIND WISHES MED ID "1"
    @Test
    void getWishes() {
        List<Wish> wishes = wishRepository.getWishes(1);

        assertThat(wishes).isNotNull();
        assertThat(wishes.size()).isEqualTo(2);
        assertThat(wishes.get(0).getProductName()).isEqualTo("AirPods Pro");
        assertThat(wishes.get(1).getProductName()).isEqualTo("Yoga måtte");
    }


    @Test
    void saveWishes() {
        Wish wish = new Wish(
                10,
                "Zoo København",
                "Kom ind og se nogle dyr",
                1,
                149.00,
                "https://www.zoo.dk/"
        );

        wishRepository.saveWishes(wish, 2);

        assertThat(wish).isNotNull();
        assertThat(wish.getProductName()).isEqualTo("Zoo København");
        assertThat(wish.getId()).isEqualTo(10);
        assertThat(wish.getLinkToProduct()).isEqualTo("https://www.zoo.dk/");
        assertThat(wish.getPrice()).isEqualTo(149.00);
        assertThat(wish.getQuantity()).isEqualTo(1);
    }

    @Test
    void removeWish() {

        List<Wish> before = wishRepository.getWishes(1);

        wishRepository.removeWish(1);

        List<Wish> after = wishRepository.getWishes(1);

        assertThat(after.size()).isNotEqualTo(before.size());
    }

    @Test
    void updateWish() {
    }
}