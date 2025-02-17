package com.example.ktpetbackend.code.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CodeGroupDto {
    private Long codeGroupId;

    private String name;

    private List<CodeDto> codes;
}
