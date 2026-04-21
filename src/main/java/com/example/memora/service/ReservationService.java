package com.example.memora.service;

import com.example.memora.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public boolean isReserved(int wishId) {
        return reservationRepository.isReserved(wishId);
    }

    public boolean isReservedByUser(int wishId, int userId) {
        if (!reservationRepository.isReserved(wishId)) {
            return false;
        }
        return reservationRepository.getReservedBy(wishId) == userId;
    }

    public void reserveWish(int wishId, int userId) {
        reservationRepository.reserve(wishId, userId);
    }

    public void unreserveWish(int wishId) {
        reservationRepository.unreserve(wishId);
    }
}