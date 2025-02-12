package com.example.ktpetbackend.user.controller;


import com.example.ktpetbackend.user.dto.*;
import com.example.ktpetbackend.user.entity.ApiResponse;
import com.example.ktpetbackend.global.security.SecurityUtil;
import com.example.ktpetbackend.user.entity.UserDetailsImpl;
import com.example.ktpetbackend.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


//    @GetMapping
//    public ResponseEntity<String> checkHeader(@RequestHeader(value = "Authorization", required = false) String authHeader) {
//        if (authHeader == null) {
//            System.out.println("❌ Authorization 헤더가 없음");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Authorization 헤더가 없음");
//        }
//        System.out.println("✅ Authorization 헤더 확인됨: " + authHeader);
//        return ResponseEntity.ok("✅ Authorization 헤더 확인됨: " + authHeader);
//    }

    @Operation(summary = "회원가입 API")
    @PostMapping("/signup")
    public ApiResponse<Long> signupUser(@RequestBody SignUpDto signUpDto){
        return new ApiResponse<>(userService.signup(signUpDto));
    }


//    @PostMapping("/signup/admin")
//    public ApiResponse<Long> signupAdmin(@RequestBody SignUpDto signUpDto){
//        return new ApiResponse<>(userService.signupAdmin(signUpDto));
//    }

    @Operation(summary = "로그인 API")
    @PostMapping("/login")
    public ApiResponse<UserInfoWithToken> login(@RequestBody LoginDto loginDto){
        return new ApiResponse<>(userService.login(loginDto));
    }


    @GetMapping("/thread")
    public void printThread(){
        SecurityUtil.printThreadInfo();
    }


    @Operation(summary = "유저 정보 조회 API")
    @GetMapping("/profile")
    public ApiResponse<UserInfo> nowUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ApiResponse<>(userService.getProfile(userDetails.getUsername()));
    }


    @Operation(summary = "회원정보 수정 API => 비밀번호, 이메일 수정")
    @PatchMapping("/modify")
    public ApiResponse<UserInfo> modifyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UserModifyDto userModifyDto){
        return new ApiResponse<>(userService.modifyInfo(userDetails.getUsername(), userModifyDto));
    }

    @Operation(summary = "회원 탈퇴 API")
    @PatchMapping("/withdraw")
    public ApiResponse<String> modifyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.withdraw(userDetails.getUsername());
        return new ApiResponse<>("탈퇴가 완료되었습니다.");
    }




//    @GetMapping("/now/admin")
//    public ApiResponse<String> nowAdmin(){
//        return new ApiResponse<>(SecurityUtil.getCurrentUsername().get());
//    }

}
