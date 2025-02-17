package com.example.ktpetbackend.code.repository;

import com.example.ktpetbackend.code.entity.CodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeGroupRepository extends JpaRepository<CodeGroup, Long> {
    List<CodeGroup> findByActive(Integer active);
}
