package com.example.ktpetbackend.pet.controller;


import com.example.ktpetbackend.pet.dto.PetInfoDto;
import com.example.ktpetbackend.pet.dto.PetRegisterDto;
import com.example.ktpetbackend.pet.repository.PetRepository;
import com.example.ktpetbackend.pet.service.PetService;
import com.example.ktpetbackend.user.entity.ApiResponse;
import com.example.ktpetbackend.user.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    //사용자 펫 등록
    @PostMapping("/register")
    public ApiResponse<String> registerPet(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PetRegisterDto petRegisterDto) {

        System.out.println(userDetails.getUsername());
        System.out.println(petRegisterDto.getName());

        petService.registerPet(userDetails.getUsername(), petRegisterDto);

        return new ApiResponse<>("등록에 성공하였습니다.");
    }

    //사용자 펫 조회
    @GetMapping("/list")
    public ApiResponse<List<PetInfoDto>> getMyPetList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ApiResponse<>(petService.getMyPetList(userDetails.getUsername()));
    }


    //사용자 펫 삭제


    //펫 정보 수정



}
