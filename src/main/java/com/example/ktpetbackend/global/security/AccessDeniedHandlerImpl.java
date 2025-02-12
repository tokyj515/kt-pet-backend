package com.example.ktpetbackend.global.security;


import com.example.ktpetbackend.user.entity.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       AccessDeniedException accessDeniedException) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

//        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("utf-8");
        ApiResponse<String> httpRes = new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "인증에 실패하였습니다.");
//        ApiResponse<String> httpRes = new ApiResponse<>("권한이 존재하지 않습니다.");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(httpRes));
    }
}