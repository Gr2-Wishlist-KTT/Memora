package com.example.memora.repository;

import com.example.memora.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;


@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_CLASS)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByEmail() {
        User user = userRepository.findUserByEmail("anna@test.com");

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("anna@test.com");
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getUsername()).isEqualTo("anna");
        assertThat(user.getPassword()).isEqualTo("anna_123");
    }

    @Test
    void saveUser() {
        User user = new User(
                3,
                "Lasse",
                "Lasse_123",
                "LasseSej@mail.com"
        );

        userRepository.saveUser(user);

        User saved = userRepository.findUserByEmail("LasseSej@mail.com");

        assertThat(user.getEmail()).isEqualTo("LasseSej@mail.com");
        assertThat(user.getUsername()).isEqualTo("Lasse");
        assertThat(user.getPassword()).isEqualTo("Lasse_123");
        assertThat(user.getId()).isEqualTo(saved.getId());
    }


}