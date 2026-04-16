package com.example.memora.repository;

import com.example.memora.model.Wishlist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Test
    void createWishlist() {
        Wishlist wishlist = new Wishlist(
                0,
                1,
                "Juleønsker"
        );

        wishlistRepository.createWishlist(wishlist, 1);
        Wishlist list = wishlistRepository.getWishList(4);

        assertThat(list).isNotNull();
        assertThat(list.getTitle()).isEqualTo("Juleønsker");
        assertThat(list.getOwner()).isEqualTo(1);
    }

    // TJEKKER ANNAS ØNSKELISTER
    @Test
    void getWishLists() {
        List<Wishlist> all = wishlistRepository.getWishLists(1);

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(2);
        assertThat(all.get(0).getTitle()).isEqualTo("Annas Fødselsdagsliste");
        assertThat(all.get(1).getTitle()).isEqualTo("Annas Hobbyting");
    }

    @Test
    void getWishList() {
        Wishlist list = wishlistRepository.getWishList(1);

        assertThat(list).isNotNull();
        assertThat(list.getTitle()).isEqualTo("Annas Fødselsdagsliste");
        assertThat(list.getId()).isEqualTo(1); // Liste nr. 1
        assertThat(list.getOwner()).isEqualTo(1); // Anna
    }

    @Test
    void updateWishlist() {
        Wishlist juleØnsker = new Wishlist(1,1,"Juleønsker");
        wishlistRepository.updateWishlist(1,juleØnsker);

        assertThat(juleØnsker).isNotNull();
        assertThat(juleØnsker.getTitle()).isEqualTo(wishlistRepository.getWishList(1).getTitle());
    }

}