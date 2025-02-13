package com.example.ktpetbackend.pet.controller;


import com.example.ktpetbackend.pet.dto.PetInfoDto;
import com.example.ktpetbackend.pet.dto.PetModifyDto;
import com.example.ktpetbackend.pet.dto.PetRegisterDto;
import com.example.ktpetbackend.pet.repository.PetRepository;
import com.example.ktpetbackend.pet.service.PetService;
import com.example.ktpetbackend.user.entity.ApiResponse;
import com.example.ktpetbackend.user.entity.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "펫 등록 API")
    @PostMapping("/register")
    public ApiResponse<String> registerPet(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PetRegisterDto petRegisterDto) {
        petService.registerPet(userDetails.getUsername(), petRegisterDto);
        return new ApiResponse<>("등록에 성공하였습니다.");
    }

    //사용자 펫 조회
    @Operation(summary = "펫 리스트 API")
    @GetMapping("/list")
    public ApiResponse<List<PetInfoDto>> getMyPetList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ApiResponse<>(petService.getMyPetList(userDetails.getUsername()));
    }

    @Operation(summary = "펫 상세정보 조회 API")
    @GetMapping("/{petId}")
    public ApiResponse<PetInfoDto> getPetInfo(@PathVariable Long petId) {
        return new ApiResponse<>(petService.getPetInfo(petId));
    }



    //사용자 펫 삭제
    @Operation(summary = "펫 삭제 API")
    @PatchMapping("/{petId}")
    public ApiResponse<String> deletePet(@PathVariable Long petId) {
        petService.deletePet(petId);
        return new ApiResponse<>("삭제에 성공하였습니다.");
    }



    //펫 정보 수정
    @Operation(summary = "펫 정보 수정 API")
    @PatchMapping("/modify")
    public ApiResponse<String> modifyPet(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PetModifyDto petModifyDto) {
        petService.modifyPet(petModifyDto);
        return new ApiResponse<>("수정에 성공하였습니다.");
    }



}
