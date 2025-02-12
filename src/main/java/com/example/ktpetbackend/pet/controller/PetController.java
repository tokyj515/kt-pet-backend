package com.example.ktpetbackend.pet.controller;


import com.example.ktpetbackend.pet.dto.PetRegisterDto;
import com.example.ktpetbackend.pet.repository.PetRepository;
import com.example.ktpetbackend.pet.service.PetService;
import com.example.ktpetbackend.user.entity.ApiResponse;
import com.example.ktpetbackend.user.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    //사용자 펫 등록
//    @PostMapping("")
//    public ApiResponse<String> registerPet(@AuthenticationPrincipal UserDetailsImpl userDetails, PetRegisterDto petRegisterDto) {
//        Long userId = Long.valueOf(userDetails.getUsername()); // 유저 ID 가져오기
//        String petName = petService.registerPet(userId, petRegisterDto);
//        return new ApiResponse<>(petName);
//    }

    //사용자 펫 조회


    //사용자 펫 삭제


    //펫 정보 수정



}
