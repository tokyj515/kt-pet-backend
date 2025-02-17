package com.example.ktpetbackend.sitter.service;

import com.example.ktpetbackend.global.exception.BadRequestException;
import com.example.ktpetbackend.sitter.dto.*;
import com.example.ktpetbackend.sitter.entity.Sitter;
import com.example.ktpetbackend.sitter.entity.SitterCarePet;
import com.example.ktpetbackend.sitter.entity.SitterCareTime;
import com.example.ktpetbackend.sitter.repository.SitterCarePetRepository;
import com.example.ktpetbackend.sitter.repository.SitterCareTimeRepository;
import com.example.ktpetbackend.sitter.repository.SitterRepository;
import com.example.ktpetbackend.user.entity.User;
import com.example.ktpetbackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        user.setUserRole("ROLE_ADMIN");
        userRepository.save(user);

        if(sitterRepository.existsByUser(user)) {
            throw new BadRequestException("이미 펫시터로 등록된 사용자입니다.");
        }

        Sitter sitter = Sitter.builder()
                .user(user)
                .location(sitterRegisterDto.getLocation())
                .charge(sitterRegisterDto.getCharge())
                .build();
        sitterRepository.save(sitter);


        for (SitterCarePetDto dto : sitterRegisterDto.getCarePetList()) {
            SitterCarePet sitterCarePet = SitterCarePet.builder()
                    .sitter(sitter)
                    .petType(dto.getPetType())
                    .build();
            sitterCarePetRepository.save(sitterCarePet);
        }

        for (SitterCareTimeDto dto : sitterRegisterDto.getCareTimeList()) {
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
                .sitterId(sitter.getId())
                .name(user.getName())
                .email(user.getEmail())
                .charge(sitter.getCharge())
                .location(sitter.getLocation())
                .carePetList(sitterCarePetDtos)
                .careTimeList(sitterCareTimeDtos)
                .build();

        return sitterInfoDto;
    }

    public SitterInfoDto getSitterProfileById(Long sitterId) {
        Sitter sitter = sitterRepository.findById(sitterId).orElseThrow(() -> new BadRequestException("시터가 존재하지 않습니다."));


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
                .sitterId(sitter.getId())
                .name(sitter.getUser().getName())
                .email(sitter.getUser().getEmail())
                .charge(sitter.getCharge())
                .location(sitter.getLocation())
                .carePetList(sitterCarePetDtos)
                .careTimeList(sitterCareTimeDtos)
                .build();

        return sitterInfoDto;
    }

    public List<SitterInfoDto> getSitterList() {
        List<Sitter> sitters = sitterRepository.findAll();

        List<SitterInfoDto> result = new ArrayList<>();

        for (Sitter sitter : sitters) {
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
                    .sitterId(sitter.getId())
                    .name(sitter.getUser().getName())
                    .email(sitter.getUser().getEmail())
                    .charge(sitter.getCharge())
                    .location(sitter.getLocation())
                    .carePetList(sitterCarePetDtos)
                    .careTimeList(sitterCareTimeDtos)
                    .build();

            result.add(sitterInfoDto);
        }

        return result;
    }


    @Transactional
    public SitterInfoDto modifyMySitterProfile(String username, SitterModifyDto sitterModifyDto) {
        // 1️⃣ 사용자 정보 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 2️⃣ 해당 사용자의 시터 프로필 가져오기
        Sitter sitter = sitterRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("시터 프로필을 찾을 수 없습니다."));

        // 3️⃣ 입력값이 null이 아닐 때만 필드 업데이트
        if (sitterModifyDto.getLocation() != null) {
            sitter.setLocation(sitterModifyDto.getLocation());
        }
        if (sitterModifyDto.getCharge() != null) {
            sitter.setCharge(sitterModifyDto.getCharge()); // String → Long 변환
        }

        // 4️⃣ 돌봄 가능 동물 수정 (기존 데이터 삭제 후 새 데이터 삽입)
        if (sitterModifyDto.getCarePetList() != null) {
            sitterCarePetRepository.deleteBySitter(sitter); // 기존 데이터 삭제
            sitterModifyDto.getCarePetList().forEach(petDto ->
                    sitterCarePetRepository.save(
                            SitterCarePet.builder()
                                    .sitter(sitter) // 연관관계 없이 sitterId 저장
                                    .petType(petDto.getPetType())
                                    .build()
                    )
            );
        }

        // 5️⃣ 돌봄 가능 시간 수정 (기존 데이터 삭제 후 새 데이터 삽입)
        if (sitterModifyDto.getCareTimeList() != null) {
            sitterCareTimeRepository.deleteBySitter(sitter); // 기존 데이터 삭제
            sitterModifyDto.getCareTimeList().forEach(timeDto ->
                    sitterCareTimeRepository.save(
                            SitterCareTime.builder()
                                    .sitter(sitter) // 연관관계 없이 sitterId 저장
                                    .day(timeDto.getDay())
                                    .startTime(timeDto.getStartTime())
                                    .endTime(timeDto.getEndTime())
                                    .build()
                    )
            );
        }

        // 6️⃣ 데이터 저장
        sitterRepository.save(sitter);

        // 7️⃣ 업데이트된 정보를 DTO로 변환하여 반환
        return SitterInfoDto.builder()
                .sitterId(sitter.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .location(sitter.getLocation())
                .charge(String.valueOf(sitter.getCharge())) // Long → String 변환
                .carePetList(sitterCarePetRepository.findBySitter(sitter).stream()
                        .map(pet -> SitterCarePetDto.builder().petType(pet.getPetType()).build())
                        .toList())
                .careTimeList(sitterCareTimeRepository.findBySitter(sitter).stream()
                        .map(time -> SitterCareTimeDto.builder()
                                .day(time.getDay())
                                .startTime(time.getStartTime())
                                .endTime(time.getEndTime())
                                .build())
                        .toList())
                .build();
    }


}