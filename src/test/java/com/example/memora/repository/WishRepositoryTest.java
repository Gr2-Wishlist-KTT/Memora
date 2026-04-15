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
                3,
                "Zoo København",
                "Kom ind og se nogle dyr",
                1,
                149.00,
                "https://www.zoo.dk/"
        );

        wishRepository.saveWishes(wish,3);

        List<Wish> saved = wishRepository.getWishes(3);
        assertThat(saved).isNotNull();
        assertThat(saved.size()).isEqualTo(2);
        assertThat(saved.get(4).getProductName()).isEqualTo("Zoo København");

    }

//    @Test
//    void saveUser() {
//        User user = new User(
//                3,
//                "Lasse",
//                "Lasse_123",
//                "LasseSej@mail.com"
//        );
//
//        userRepository.saveUser(user);
//
//        User saved = userRepository.findUserByEmail("LasseSej@mail.com");
//
//        assertThat(user.getEmail()).isEqualTo("LasseSej@mail.com");
//        assertThat(user.getUsername()).isEqualTo("Lasse");
//        assertThat(user.getPassword()).isEqualTo("Lasse_123");
//        assertThat(user.getId()).isEqualTo(saved.getId());
//    }

    @Test
    void removeWish() {
    }

    @Test
    void updateWish() {
    }
}