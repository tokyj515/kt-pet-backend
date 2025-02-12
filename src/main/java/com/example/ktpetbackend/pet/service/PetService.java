package com.example.ktpetbackend.pet.service;

import com.example.ktpetbackend.pet.repository.PetRepository;
import com.example.ktpetbackend.user.entity.User;
import com.example.ktpetbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;


    public String registerPet(Long userId) {
        User user = userRepository.findById(userId).get();



    }
}
