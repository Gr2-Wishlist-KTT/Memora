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

    // FIND WISH MED ID "1" FRA EN LISTE
    @Test
    void getWishes() {
        List<Wish> wishes = wishRepository.getWishes(1);

        assertThat(wishes).isNotNull();
        assertThat(wishes.size()).isEqualTo(2);
        assertThat(wishes.get(0).getProductName()).isEqualTo("AirPods Pro");
        assertThat(wishes.get(1).getProductName()).isEqualTo("Yoga måtte");
    }

    //FIND WISH MED ID "1" FRA DATABASEN
@Test
void findWish(){
       Wish wish = wishRepository.findWish(1);

        assertThat(wish.getProductName()).isEqualTo("AirPods Pro");
        assertThat(wish.getId()).isEqualTo(1);
        assertThat(wish.getPrice()).isEqualTo(wishRepository.findWish(1).getPrice());
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
    void removeWish(){

     Wish wish = wishRepository.findWish(1);
     assertThat(wish).isNotNull();

     wishRepository.removeWish(1);
     assertThrows(Exception.class, () -> {wishRepository.findWish(1);
     });

    }

    @Test
    void updateWish() {
        Wish wish = new Wish(1, "Bowling Kugle", "Str. 9, med flammer, 8kg", 1, 2294.00, "https://bowling-stars.com/da/collections/bowlingkugler/products/sensor-solid-kugle?_pos=73&_fid=134d91da3&_ss=c&variant=48730750910791");
        wishRepository.updateWish(wish);

        assertThat(wish).isNotNull();
        assertThat(wish.getProductName()).isEqualTo("Bowling Kugle");
        assertThat(wish.getId()).isEqualTo(1);
        assertThat(wish.getLinkToProduct()).isEqualTo("https://bowling-stars.com/da/collections/bowlingkugler/products/sensor-solid-kugle?_pos=73&_fid=134d91da3&_ss=c&variant=48730750910791");


    }
}