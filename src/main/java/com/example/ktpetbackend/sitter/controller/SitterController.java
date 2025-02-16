package com.example.ktpetbackend.sitter.controller;


import com.example.ktpetbackend.sitter.dto.SitterInfoDto;
import com.example.ktpetbackend.sitter.dto.SitterRegisterDto;
import com.example.ktpetbackend.sitter.repository.SitterRepository;
import com.example.ktpetbackend.sitter.service.SitterService;
import com.example.ktpetbackend.user.dto.SignUpDto;
import com.example.ktpetbackend.user.entity.ApiResponse;
import com.example.ktpetbackend.user.entity.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sitter")
public class SitterController {

    private final SitterService sitterService;

    //펫시터 프로필 등록
    //이름, 연락처, 위치, 돌봄 가능 반려동물 종류, 돌봄 가능 시간, 요금 입력
    @Operation(summary = "펫시터 등록 API")
    @PostMapping("/register")
    public ApiResponse<String> registerSitter(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @RequestBody SitterRegisterDto sitterRegisterDto) {
        sitterService.registerSitter(userDetails.getUsername(), sitterRegisterDto);
        return new ApiResponse<>("시터 등록에 성공하였습니다.");
    }

    @Operation(summary = "펫시터 프로필 (내가 펫시터인 경우에 사용)API")
    @GetMapping("/profile")
    public ApiResponse<SitterInfoDto> getSitterProfile(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ApiResponse<>(sitterService.getSitterProfile(userDetails.getUsername()));
    }

    @Operation(summary = "펫시터 프로필 상세 보기 API")
    @GetMapping("/profile/{sitterId}")
    public ApiResponse<SitterInfoDto> getSitterProfile(@PathVariable Long sitterId){
        return new ApiResponse<>(sitterService.getSitterProfileById(sitterId));
    }

    @Operation(summary = "펫시터 프로필 상세 보기 API")
    @GetMapping("/profile/list")
    public ApiResponse<List<SitterInfoDto>> getSitterList(){
        return new ApiResponse<>(sitterService.getSitterList());
    }


}
