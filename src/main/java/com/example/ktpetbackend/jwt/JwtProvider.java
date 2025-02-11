package com.example.ktpetbackend.jwt;

import com.example.ktpetbackend.dto.Token;
import com.example.ktpetbackend.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private Key key; // ✅ JWT 서명용 키
    private static final long ACCESS_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000L; // 7일
    private static final long REFRESH_TOKEN_VALIDITY = 30 * 24 * 60 * 60 * 1000L; // 30일

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @PostConstruct
    protected void init() {
        // ✅ 강력한 보안 키 생성 (환경 변수 사용 권장)
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    /**
     * 🔹 JWT 토큰 생성 (Access & Refresh) - username을 key로 저장
     */
    public Token generateTokens(String username) {
        return Token.builder()
                .accessToken(generateToken(username, "ROLE_USER", ACCESS_TOKEN_VALIDITY))
                .refreshToken(generateToken(username, "ROLE_USER", REFRESH_TOKEN_VALIDITY))
                .key(username) // ✅ AccessToken에도 username을 key로 저장
                .build();
    }

    /**
     * 🔹 Access Token 생성 (username + roles 포함)
     */
    public String generateToken(String username, String role, long validity) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", role); // ✅ 권한 정보 추가

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(key, SignatureAlgorithm.HS256) // ✅ 안전한 키로 서명
                .compact();
    }

    /**
     * 🔹 JWT에서 사용자 역할(Role) 추출
     */
    public String getUserRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", String.class);
    }

    /**
     * 🔹 JWT에서 사용자명(username) 추출
     */
    public String getUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject(); // ✅ subject에 저장된 username 반환
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject(); // ✅ 만료된 경우에도 username 반환
        } catch (Exception e) {
            return null; // ❌ 오류 발생 시 null 반환
        }
    }

    /**
     * 🔹 JWT에서 Authentication 객체 생성 (username 기반)
     */
    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        if (username == null) return null;

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) return null;

        String role = getUserRole(token);
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    /**
     * 🔹 HTTP 요청에서 JWT 토큰 추출
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken == null) {
            System.out.println("❌ Authorization 헤더가 없음");
            return null;
        }

        if (!bearerToken.startsWith("Bearer ")) {
            System.out.println("❌ Authorization 헤더가 'Bearer '로 시작하지 않음: " + bearerToken);
            return null;
        }

        return bearerToken.substring(7);
    }

    /**
     * 🔹 JWT 유효성 검사
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("❌ JWT가 만료됨: " + e.getClaims().getExpiration());
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("❌ JWT가 유효하지 않음: " + e.getMessage());
            return false;
        }
    }
}
