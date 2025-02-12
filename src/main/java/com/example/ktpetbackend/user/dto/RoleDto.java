package com.example.ktpetbackend.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoleDto {
    private String username;
    private Long id;
    private String password;
    private List<String> roles;
}