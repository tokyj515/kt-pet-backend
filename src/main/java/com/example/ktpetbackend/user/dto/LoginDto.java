package com.example.ktpetbackend.user.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginDto {
    private String username;
    private String password;
}