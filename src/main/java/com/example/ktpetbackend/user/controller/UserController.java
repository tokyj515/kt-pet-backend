package com.example.ktpetbackend.user.controller;


import com.example.ktpetbackend.user.dto.LoginDto;
import com.example.ktpetbackend.user.dto.SignUpDto;
import com.example.ktpetbackend.user.dto.UserInfoWithToken;
import com.example.ktpetbackend.user.entity.ApiResponse;
import com.example.ktpetbackend.global.security.SecurityUtil;
import com.example.ktpetbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<String> checkHeader(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null) {
            System.out.println("❌ Authorization 헤더가 없음");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Authorization 헤더가 없음");
        }
        System.out.println("✅ Authorization 헤더 확인됨: " + authHeader);
        return ResponseEntity.ok("✅ Authorization 헤더 확인됨: " + authHeader);
    }


    @PostMapping("/signup/user")
    public ApiResponse<Long> signupUser(@RequestBody SignUpDto signUpDto){
        return new ApiResponse<>(userService.signup(signUpDto));
    }


    @PostMapping("/signup/admin")
    public ApiResponse<Long> signupAdmin(@RequestBody SignUpDto signUpDto){
        return new ApiResponse<>(userService.signupAdmin(signUpDto));
    }

    @PostMapping("/login")
    public ApiResponse<UserInfoWithToken> login(@RequestBody LoginDto loginDto){
        return new ApiResponse<>(userService.login(loginDto));
    }


    @GetMapping("/thread")
    public void printThread(){
        SecurityUtil.printThreadInfo();
    }


    @GetMapping("/now/user")
    public ApiResponse<String> nowUser(){
        return new ApiResponse<>(SecurityUtil.getCurrentUsername().get());
    }


    @GetMapping("/now/admin")
    public ApiResponse<String> nowAdmin(){
        return new ApiResponse<>(SecurityUtil.getCurrentUsername().get());
    }

}
