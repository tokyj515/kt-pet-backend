package com.example.ktpetbackend.code.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CodeDto {
    private Long codeGroupId;

    private Long codeId;

    private String name;

//    private Integer order;

}
