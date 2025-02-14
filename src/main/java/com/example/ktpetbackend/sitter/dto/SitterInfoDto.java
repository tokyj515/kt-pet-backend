package com.example.ktpetbackend.sitter.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SitterInfoDto {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private String location;

    private String charge;


    private List<SitterCarePetDto> carePetList;

    private List<SitterCareTimeDto> careTimeList;


}
