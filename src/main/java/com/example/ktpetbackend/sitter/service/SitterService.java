package com.example.ktpetbackend.sitter.service;

import com.example.ktpetbackend.global.exception.BadRequestException;
import com.example.ktpetbackend.sitter.dto.SitterCarePetDto;
import com.example.ktpetbackend.sitter.dto.SitterCareTimeDto;
import com.example.ktpetbackend.sitter.dto.SitterInfoDto;
import com.example.ktpetbackend.sitter.dto.SitterRegisterDto;
import com.example.ktpetbackend.sitter.entity.Sitter;
import com.example.ktpetbackend.sitter.entity.SitterCarePet;
import com.example.ktpetbackend.sitter.entity.SitterCareTime;
import com.example.ktpetbackend.sitter.repository.SitterCarePetRepository;
import com.example.ktpetbackend.sitter.repository.SitterCareTimeRepository;
import com.example.ktpetbackend.sitter.repository.SitterRepository;
import com.example.ktpetbackend.user.entity.User;
import com.example.ktpetbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SitterService {

    private final UserRepository userRepository;
    private final SitterRepository sitterRepository;
    private final SitterCarePetRepository sitterCarePetRepository;
    private final SitterCareTimeRepository sitterCareTimeRepository;


    public void registerSitter(String username, SitterRegisterDto sitterRegisterDto) {
        User user = userRepository.findByUsername(username).get();

        Sitter sitter = Sitter.builder()
                .user(user)
                .location(sitterRegisterDto.getLocation())
                .charge(sitterRegisterDto.getCharge())
                .build();
        sitterRepository.save(sitter);


        for(SitterCarePetDto dto : sitterRegisterDto.getCarePetList()){
            SitterCarePet sitterCarePet = SitterCarePet.builder()
                    .sitter(sitter)
                    .petType(dto.getPetType())
                    .build();
            sitterCarePetRepository.save(sitterCarePet);
        }

        for(SitterCareTimeDto dto : sitterRegisterDto.getCareTimeList()){
            SitterCareTime sitterCareTime = SitterCareTime.builder()
                    .sitter(sitter)
                    .day(dto.getDay())
                    .startTime(dto.getStartTime())
                    .endTime(dto.getEndTime())
                    .build();
            sitterCareTimeRepository.save(sitterCareTime);
        }
    }

    public SitterInfoDto getSitterProfile(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new BadRequestException("펫시터로 등록된 사용자가 아닙니다.")
        );
        Sitter sitter = sitterRepository.findByUser(user).get();

        List<SitterCarePetDto> sitterCarePetDtos = sitterCarePetRepository.findBySitter(sitter)
                .stream()
                .map(sitterCarePet -> SitterCarePetDto.builder()
                        .petType(sitterCarePet.getPetType())
                        .build())
                .collect(Collectors.toList());

        List<SitterCareTimeDto> sitterCareTimeDtos = sitterCareTimeRepository.findBySitter(sitter)
                .stream()
                .map(sitterCareTime -> SitterCareTimeDto.builder()
                        .day(sitterCareTime.getDay())
                        .startTime(sitterCareTime.getStartTime())
                        .endTime(sitterCareTime.getEndTime())
                        .build())
                .collect(Collectors.toList());


        SitterInfoDto sitterInfoDto = SitterInfoDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .charge(sitter.getCharge())
                .location(sitter.getLocation())
                .carePetList(sitterCarePetDtos)
                .careTimeList(sitterCareTimeDtos)
                .build();

        return sitterInfoDto;
    }
}
