package com.example.ktpetbackend.pet.repository;


import com.example.ktpetbackend.pet.entity.Pet;
import com.example.ktpetbackend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findPetByUser(User user);
    List<Pet> findPetByUserAndDeleted(User user, Integer deleted);
}
