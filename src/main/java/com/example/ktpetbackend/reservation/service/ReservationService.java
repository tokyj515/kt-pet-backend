package com.example.ktpetbackend.reservation.service;

import com.example.ktpetbackend.global.exception.BadRequestException;
import com.example.ktpetbackend.pet.entity.Pet;
import com.example.ktpetbackend.pet.repository.PetRepository;
import com.example.ktpetbackend.reservation.dto.ReservationDto;
import com.example.ktpetbackend.reservation.dto.ReservationRegisterDto;
import com.example.ktpetbackend.reservation.entity.Reservation;
import com.example.ktpetbackend.reservation.entity.ReservationCareTime;
import com.example.ktpetbackend.reservation.repository.ReservationCareTimeRepository;
import com.example.ktpetbackend.reservation.repository.ReservationRepository;
import com.example.ktpetbackend.sitter.dto.SitterCareTimeDto;
import com.example.ktpetbackend.sitter.entity.Sitter;
import com.example.ktpetbackend.sitter.repository.SitterRepository;
import com.example.ktpetbackend.user.entity.User;
import com.example.ktpetbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final SitterRepository sitterRepository;
    private final PetRepository petRepository;
    private final ReservationCareTimeRepository reservationCareTimeRepository;


    public void registerReservation(String username, ReservationRegisterDto reservationRegisterDto) {
        // ✅ 1. 예약자 정보 가져오기
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        // ✅ 2. 펫시터 정보 가져오기
        Sitter sitter = sitterRepository.findById(reservationRegisterDto.getSitterId())
                .orElseThrow(() -> new IllegalArgumentException("해당 펫시터를 찾을 수 없습니다."));

        if(user == sitter.getUser()) {
            throw new BadRequestException("사용자와 펫시터 정보가 동일합니다. 다른 펫시터를 선택해 주세요!");
        }

        // ✅ 3. 예약할 펫 정보 가져오기
        Pet pet = petRepository.findById(reservationRegisterDto.getPetId())
                .orElseThrow(() -> new IllegalArgumentException("해당 펫을 찾을 수 없습니다."));


        // ✅ 5. 예약 생성
        Reservation reservation = Reservation.builder()
                .user(user) // 예약자 정보
                .sitter(sitter) // 펫시터 정보
                .pet(pet) // 예약할 펫 정보
                .totalCharge(reservationRegisterDto.getTotalCharge()) // 총 요금
                .build();

        // ✅ 6. 예약 저장
        reservationRepository.save(reservation);


        // ✅ 5. 예약 시간 생성
        for(SitterCareTimeDto dto : reservationRegisterDto.getCareTimeDtoList()){
            ReservationCareTime reservationCareTime = ReservationCareTime.builder()
                    .reservation(reservation)
                    .day(dto.getDay())
                    .startTime(dto.getStartTime())
                    .endTime(dto.getEndTime())
                    .build();

            // ✅ 6. 예약 시간 저장
            reservationCareTimeRepository.save(reservationCareTime);
        }


    }

    /**
     * ✅ 사용자의 예약 목록 조회
     */
    @Transactional(readOnly = true)
    public List<ReservationDto> selectReservationUserList(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        List<ReservationDto> result = new ArrayList<>();
        List<Reservation> reservations = reservationRepository.findByUser(user);

        for (Reservation reservation : reservations) {
            result.add(convertToReservationDto(reservation));
        }
        return result;
    }

    /**
     * ✅ 펫시터의 예약 목록 조회
     */
    @Transactional(readOnly = true)
    public List<ReservationDto> selectReservationSitterList(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Sitter sitter = sitterRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("펫시터 정보를 찾을 수 없습니다."));

        List<ReservationDto> result = new ArrayList<>();
        List<Reservation> reservations = reservationRepository.findBySitter(sitter);

        for (Reservation reservation : reservations) {
            result.add(convertToReservationDto(reservation));
        }
        return result;
    }

    /**
     * ✅ 예약 상세 조회
     */
    @Transactional(readOnly = true)
    public ReservationDto selectReservationDetail(String username, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다."));

        // 예약한 사용자가 맞는지 검증
        if (!reservation.getUser().getUsername().equals(username) && !reservation.getSitter().getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("해당 예약을 조회할 권한이 없습니다.");
        }

        return convertToReservationDto(reservation);
    }

    /**
     * ✅ 펫시터가 예약 승인
     */
    @Transactional
    public void confirmReservation(String username, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다."));

        // 예약 승인 권한 확인 (펫시터 본인만 가능)
        if (!reservation.getSitter().getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("해당 예약을 승인할 권한이 없습니다.");
        }

        reservation.setConfirm(1); // 예약 승인 (0: 대기, 1: 승인)
        reservationRepository.save(reservation);
    }

    /**
     * ✅ 예약 엔티티 → DTO 변환
     */
    private ReservationDto convertToReservationDto(Reservation reservation) {
        List<ReservationCareTime> reservationCareTimes = reservationCareTimeRepository.findByReservation(reservation);

        List<SitterCareTimeDto> sitterCareTimeDtos = new ArrayList<>();
        for (ReservationCareTime sitterCareTime : reservationCareTimes) {
            sitterCareTimeDtos.add(SitterCareTimeDto.builder()
                    .day(sitterCareTime.getDay())
                    .startTime(sitterCareTime.getStartTime())
                    .endTime(sitterCareTime.getEndTime())
                    .build());
        }

        return ReservationDto.builder()
                .pet(reservation.getPet())
                .sitter(reservation.getSitter())
                .confirm(reservation.getConfirm())
                .totalCharge(reservation.getTotalCharge())
                .sitterCareTimeDtos(sitterCareTimeDtos)
                .build();
    }
}
