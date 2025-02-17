package com.example.ktpetbackend.reservation.repository;

import com.example.ktpetbackend.reservation.entity.Reservation;
import com.example.ktpetbackend.reservation.entity.ReservationCareTime;
import com.example.ktpetbackend.sitter.entity.Sitter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationCareTimeRepository extends JpaRepository<ReservationCareTime, Integer> {
    List<ReservationCareTime> findByReservation(Reservation reservation);
}
