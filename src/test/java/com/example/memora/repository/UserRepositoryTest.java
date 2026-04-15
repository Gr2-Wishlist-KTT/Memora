package com.example.memora.repository;

import com.example.memora.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;


@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_CLASS)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail() {
        User user = userRepository.findUserByEmail("anna@test.com");

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("anna@test.com");
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getUsername()).isEqualTo("anna");
        assertThat(user.getPassword()).isEqualTo("anna123");
    }


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findUserByEmail() {
    }

    @Test
    void saveUser() {
    }



}