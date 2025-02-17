package com.example.ktpetbackend.reservation.controller;




import com.example.ktpetbackend.reservation.dto.ReservationDto;
import com.example.ktpetbackend.reservation.dto.ReservationRegisterDto;
import com.example.ktpetbackend.reservation.service.ReservationService;
import com.example.ktpetbackend.user.entity.ApiResponse;
import com.example.ktpetbackend.user.entity.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;


    // 사용자 -> 펫시터 예약
    @Operation(summary = "펫시터 예약하기 API")
    @PostMapping("/register")
    public ApiResponse<String> registerReservation (@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @RequestBody ReservationRegisterDto reservationRegisterDto) {
        reservationService.registerReservation(userDetails.getUsername(), reservationRegisterDto);
        return new ApiResponse<>("펫시터가 예약되었습니다.");
    }


    @Operation(summary = "사용자의 예약 목록 확인하기 API")
    @GetMapping("/user/list")
    public ApiResponse<List<ReservationDto>> selectReservationUserList (@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ApiResponse<>(reservationService.selectReservationUserList(userDetails.getUsername()));
    }

    @Operation(summary = "펫시터의 예약 목록 확인하기 API")
    @GetMapping("/sitter/list")
    public ApiResponse<List<ReservationDto>> selectReservationSitterList (@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ApiResponse<>(reservationService.selectReservationSitterList(userDetails.getUsername()));
    }


    @Operation(summary = "예약 상세 내역 API")
    @GetMapping("/{reservationId}")
    public ApiResponse<ReservationDto> selectReservationDetail (@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable Long reservationId) {
        return new ApiResponse<>(reservationService.selectReservationDetail(userDetails.getUsername(), reservationId));
    }

    @Operation(summary = "펫시터가 승인 API")
    @PatchMapping("/{reservationId}")
    public ApiResponse<String> confirmReservation (@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @PathVariable Long reservationId) {
        reservationService.confirmReservation(userDetails.getUsername(), reservationId);
        return new ApiResponse<>("펫시터 예약이 확정되었습니다.");
    }

}
