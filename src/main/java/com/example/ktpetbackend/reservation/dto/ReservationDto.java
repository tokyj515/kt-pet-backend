package com.example.ktpetbackend.reservation.dto;


import com.example.ktpetbackend.pet.dto.PetInfoDto;
import com.example.ktpetbackend.pet.entity.Pet;
import com.example.ktpetbackend.sitter.dto.SitterCareTimeDto;
import com.example.ktpetbackend.sitter.dto.SitterInfoDto;
import com.example.ktpetbackend.sitter.entity.Sitter;
import com.example.ktpetbackend.user.dto.UserInfo;
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
    private Long reservationId;

    private PetInfoDto pet;

    private SitterInfoDto sitter;

    private UserInfo user;

    private List<SitterCareTimeDto> sitterCareTimeDtos;

    private Integer totalCharge;

    private Integer confirm;

}
