package com.example.ktpetbackend.reservation.repository;

import com.example.ktpetbackend.reservation.entity.Reservation;
import com.example.ktpetbackend.sitter.entity.Sitter;
import com.example.ktpetbackend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByUser(User user);

    List<Reservation> findBySitter(Sitter sitter);

    Optional<Reservation> findById(Long id);
}
