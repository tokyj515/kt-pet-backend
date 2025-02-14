package com.example.ktpetbackend.sitter.repository;

import com.example.ktpetbackend.sitter.entity.Sitter;
import com.example.ktpetbackend.sitter.entity.SitterCarePet;
import com.example.ktpetbackend.sitter.entity.SitterCareTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SitterCareTimeRepository extends JpaRepository<SitterCareTime, Long> {

    List<SitterCareTime> findBySitter(Sitter sitter);
}
