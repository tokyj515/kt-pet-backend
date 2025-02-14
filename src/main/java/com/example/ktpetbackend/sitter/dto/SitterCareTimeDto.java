package com.example.ktpetbackend.sitter.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SitterCareTimeDto {
    private String day;

    private String startTime;

    private String endTime;
}
