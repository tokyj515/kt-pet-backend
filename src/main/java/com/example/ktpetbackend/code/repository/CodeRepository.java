package com.example.ktpetbackend.code.repository;

import com.example.ktpetbackend.code.entity.Code;
import com.example.ktpetbackend.code.entity.CodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {

    List<Code> findByActiveAndCodeGroup(Integer active, CodeGroup codeGroup);

//    List<Code> findByActiveAndCodeGroupOrderByOrder(Integer active, CodeGroup codeGroup);
}
