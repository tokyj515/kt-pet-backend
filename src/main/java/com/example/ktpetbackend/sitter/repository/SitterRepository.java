package com.example.ktpetbackend.sitter.repository;

import com.example.ktpetbackend.sitter.entity.Sitter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SitterRepository extends JpaRepository<Sitter, Long> {
}
