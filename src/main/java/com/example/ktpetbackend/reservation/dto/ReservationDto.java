package com.example.ktpetbackend.reservation.dto;


import com.example.ktpetbackend.pet.entity.Pet;
import com.example.ktpetbackend.sitter.dto.SitterCareTimeDto;
import com.example.ktpetbackend.sitter.entity.Sitter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationDto {

    private Pet pet;

    private Sitter sitter;

    private List<SitterCareTimeDto> sitterCareTimeDtos;

    private Integer totalCharge;

    private Integer confirm;

}
