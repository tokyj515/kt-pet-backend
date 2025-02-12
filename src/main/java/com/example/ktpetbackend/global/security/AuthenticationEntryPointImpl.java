package com.example.ktpetbackend.global.security;


import com.example.ktpetbackend.user.entity.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException ex) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("utf-8");
        ApiResponse<String> httpRes = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(),"인증에 실패하였습니다.");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(httpRes));
    }
}
