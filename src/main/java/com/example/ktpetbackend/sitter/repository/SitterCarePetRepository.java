package com.example.ktpetbackend.sitter.repository;

import com.example.ktpetbackend.sitter.entity.Sitter;
import com.example.ktpetbackend.sitter.entity.SitterCarePet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SitterCarePetRepository extends JpaRepository<SitterCarePet, Long> {

    List<SitterCarePet> findBySitter(Sitter sitter);

    void deleteBySitter(Sitter sitter);
}
