package com.example.ktpetbackend.pet.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PetModifyDto {

    private Long petId;

    private String name;

    private String petType;

    private Long age;
}
