package com.example.ktpetbackend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoleDto {
    private Long id;
    private String password;
    private List<String> roles;
}