package com.example.ktpetbackend.reservation.entity;


import com.example.ktpetbackend.pet.entity.Pet;
import com.example.ktpetbackend.sitter.entity.Sitter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "`reservation_care_time`")
public class ReservationCareTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private String day;

    private String startTime;

    private String endTime;

}
