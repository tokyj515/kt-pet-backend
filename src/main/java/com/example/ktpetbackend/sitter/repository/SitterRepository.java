package com.example.ktpetbackend.sitter.repository;

import com.example.ktpetbackend.sitter.entity.Sitter;
import com.example.ktpetbackend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SitterRepository extends JpaRepository<Sitter, Long> {
    Optional<Sitter> findByUser(User user);

    boolean existsByUser(User user);
}
