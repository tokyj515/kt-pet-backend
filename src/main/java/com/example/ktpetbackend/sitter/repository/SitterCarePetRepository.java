package com.example.ktpetbackend.sitter.repository;

import com.example.ktpetbackend.sitter.entity.SitterCarePet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SitterCarePetRepository extends JpaRepository<SitterCarePet, Long> {
}
