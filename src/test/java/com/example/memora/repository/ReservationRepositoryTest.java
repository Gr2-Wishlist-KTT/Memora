package com.example.memora.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void isReserved() {
        boolean result = reservationRepository.isReserved(1);

        assertTrue(result);
    }

    @Test
    void getReservedBy() {
        int userId = reservationRepository.getReservedBy(1);

        assertEquals(2, userId);
    }

    @Test
    void reserve() {
        reservationRepository.reserve(4, 3);

        boolean result = reservationRepository.isReserved(4);
        assertTrue(result);

        int userId = reservationRepository.getReservedBy(4);
        assertEquals(3, userId);
    }

    @Test
    void unreserve() {
        reservationRepository.unreserve(1);
        boolean result = reservationRepository.isReserved(1);
        assertFalse(result);
    }


}