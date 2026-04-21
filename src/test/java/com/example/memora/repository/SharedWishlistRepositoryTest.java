package com.example.memora.repository;

import com.example.memora.model.SharedWishlist;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class SharedWishlistRepositoryTest {

    @Autowired
    private SharedWishlistRepository sharedWishlistRepository;

    @Test
    void findSharesForUser() {
        List<SharedWishlist> sharedWishlist = sharedWishlistRepository.findSharesForUser(1);
        assertThat(sharedWishlist).isNotNull();
        assertThat(sharedWishlist.size()).isEqualTo(1);

    }

    @Test
    void findViewersForWishlist() {
        List<SharedWishlist> sharedWishlist = sharedWishlistRepository.findViewersForWishlist(1);

        assertThat(sharedWishlist).isNotNull();
        assertThat(sharedWishlist.size()).isEqualTo(2);

    }

    @Test
    void addShare() {

        sharedWishlistRepository.addShare(2,1);
        boolean exists = sharedWishlistRepository.existsShare(2,1);

        assertTrue(exists);

    }

    @Test
    void existsShare() {
        boolean exists = sharedWishlistRepository.existsShare(1, 2);

        assertTrue(exists);
    }

    @Test
    void deleteShare() {
        sharedWishlistRepository.deleteShare(1, 2);

        boolean exists = sharedWishlistRepository.existsShare(1, 2);
        assertFalse(exists);
    }
}