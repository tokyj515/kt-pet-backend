package com.example.ktpetbackend.user.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserModifyDto {
    private String newPassword;
    private String email;
}