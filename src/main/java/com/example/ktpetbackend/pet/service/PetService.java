package com.example.ktpetbackend.pet.service;

import com.example.ktpetbackend.pet.dto.PetInfoDto;
import com.example.ktpetbackend.pet.dto.PetModifyDto;
import com.example.ktpetbackend.pet.dto.PetRegisterDto;
import com.example.ktpetbackend.pet.entity.Pet;
import com.example.ktpetbackend.pet.repository.PetRepository;
import com.example.ktpetbackend.user.entity.User;
import com.example.ktpetbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;

    public void registerPet(String username, PetRegisterDto petRegisterDto) {
        User user = userRepository.findByUsername(username).get();

        Pet pet = Pet.builder()
                .name(petRegisterDto.getName())
                .petType(petRegisterDto.getPetType())
                .age(petRegisterDto.getAge())
                .user(user)
                .deleted(0)
                .build();

        petRepository.save(pet);
    }


    public List<PetInfoDto> getMyPetList(String username) {

        User user = userRepository.findByUsername(username).get();
        List<Pet> list = petRepository.findPetByUserAndDeleted(user, 0);
        List<PetInfoDto> petInfoDtoList = new ArrayList<>();

        for (Pet pet : list) {
            PetInfoDto e = PetInfoDto.builder()
                    .petId(pet.getId())
                    .name(pet.getName())
                    .petType(pet.getPetType())
                    .age(pet.getAge())
                    .url(pet.getUrl())
                    .deleted(pet.getDeleted())
                    .build();
            petInfoDtoList.add(e);
        }

        return petInfoDtoList;
    }

    public void modifyPet(PetModifyDto petModifyDto) {
        Pet pet = petRepository.findById(petModifyDto.getPetId()).get();

        if(petModifyDto.getPetType() != null) {
            pet.setPetType(petModifyDto.getPetType());
        }
        if(petModifyDto.getAge() != null) {
            pet.setAge(petModifyDto.getAge());
        }
        if (petModifyDto.getName() != null) {
            pet.setName(petModifyDto.getName());
        }

        petRepository.save(pet);

    }

    public PetInfoDto getPetInfo(Long petId) {
        Pet pet = petRepository.findById(petId).get();

        PetInfoDto e = PetInfoDto.builder()
                .petId(pet.getId())
                .name(pet.getName())
                .petType(pet.getPetType())
                .age(pet.getAge())
                .url(pet.getUrl())
                .deleted(pet.getDeleted())
                .build();

        return e;
    }

    public void deletePet(Long petId) {
        Pet pet = petRepository.findById(petId).get();

        pet.setDeleted(1);
        petRepository.save(pet);
    }
}
