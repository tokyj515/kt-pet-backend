package com.example.ktpetbackend.reservation.dto;


import com.example.ktpetbackend.sitter.dto.SitterCareTimeDto;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationRegisterDto {

    private Long sitterId;

    private Long petId;

    List<SitterCareTimeDto> careTimeDtoList;

    private Integer totalCharge;

}
