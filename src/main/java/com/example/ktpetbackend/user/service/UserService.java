package com.example.ktpetbackend.user.service;

import com.example.ktpetbackend.global.exception.BadRequestException;
import com.example.ktpetbackend.user.dto.*;
import com.example.ktpetbackend.user.entity.RefreshToken;
import com.example.ktpetbackend.user.entity.User;
import com.example.ktpetbackend.global.exception.NotFoundException;
import com.example.ktpetbackend.global.jwt.JwtProvider;
import com.example.ktpetbackend.user.repository.RefreshTokenRepository;
import com.example.ktpetbackend.user.repository.UserRepository;
import com.example.ktpetbackend.global.security.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 🔹 현재 로그인한 사용자 ID 가져오기
     */
    public Long getUserId() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findByUsername)
                .map(User::getId)
                .orElseThrow(() -> new NotFoundException("로그인한 사용자를 찾을 수 없습니다."));
    }

    /**
     * 🔹 사용자 ID로 사용자 정보 조회
     */
    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));
    }

    /**
     * 🔹 사용자명(username)으로 사용자 ID 조회
     */
    public Long findUserIdByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::getId)
                .orElse(0L);
    }

    /**
     * 🔹 회원가입 (기본 사용자)
     */
    public Long signup(SignUpDto signUpDto) {
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());

        User user = User.builder()
                .username(signUpDto.getUsername())
                .password(encodedPassword)
                .name(signUpDto.getName())
                .userRole("ROLE_USER")
                .email(signUpDto.getEmail())
                .phone(signUpDto.getPhone())
                .deleted(0)
                .build();

        userRepository.save(user);
        return findUserIdByUsername(signUpDto.getUsername());
    }

    /**
     * 🔹 관리자 회원가입
     */
    public Long signupAdmin(SignUpDto signUpDto) {
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());

        User user = User.builder()
                .username(signUpDto.getUsername())
                .password(encodedPassword)
                .name(signUpDto.getName())
                .userRole("ROLE_ADMIN")
                .build();

        userRepository.save(user);
        return findUserIdByUsername(signUpDto.getUsername());
    }

    /**
     * 🔹 로그인 처리 및 토큰 발급
     */
    public UserInfoWithToken login(LoginDto loginDto) {
        User user = userRepository.findByUsernameAndDeleted(loginDto.getUsername(), 0)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String refreshToken = jwtProvider.generateToken(user.getUsername(), "ROLE_USER", 30 * 24 * 60 * 60 * 1000L);
        refreshTokenRepository.save(new RefreshToken(user, refreshToken));

        return UserInfoWithToken.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(jwtProvider.generateTokens(loginDto.getUsername()))
                .userRole(user.getUserRole())
                .build();
    }

    /**
     * 🔹 현재 로그인한 사용자 정보 조회
     */
//    public User findNowLoginUser() {
//        return SecurityUtil.getCurrentUsername()
//                .flatMap(userRepository::findByUsername)
//                .orElseThrow(() -> new NotFoundException("현재 로그인한 사용자를 찾을 수 없습니다."));
//    }

    public UserInfo getProfile(String username) {
        User user = userRepository.findByUsername(username).get();

        UserInfo userInfo = UserInfo.builder()
                .username(user.getUsername())
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();

        return userInfo;
    }

    @Transactional
    public UserInfo modifyInfo(String username, UserModifyDto userModifyDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (userModifyDto.getNewPassword() != null) {
            user.setPassword(passwordEncoder.encode(userModifyDto.getNewPassword()));
        }
        if (userModifyDto.getEmail() != null) {
            user.setEmail(userModifyDto.getEmail());
        }

        userRepository.save(user);

        return UserInfo.builder()
                .username(user.getUsername())
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .build();
    }


    public void withdraw(String username) {
        User user = userRepository.findByUsername(username).get();

        user.setDeleted(1);

        userRepository.save(user);
    }
}
