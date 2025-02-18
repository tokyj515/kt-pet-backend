package com.example.ktpetbackend.payment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 결제 ID

    private String merchantUid; // 결제 고유 주문번호
    private String buyerName; // 구매자 이름
    private String buyerEmail; // 구매자 이메일
    private String buyerTel; // 구매자 전화번호
    private Integer amount; // 결제 금액
    private LocalDateTime paymentDate; // 결제 시간
}
