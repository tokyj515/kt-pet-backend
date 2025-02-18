package com.example.ktpetbackend.payment.service;

import com.example.ktpetbackend.payment.entity.Payment;
import com.example.ktpetbackend.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    // ✅ 결제 정보 저장
    @Transactional
    public Payment savePayment(String merchantUid, String buyerName, String buyerEmail, String buyerTel, Integer amount) {
        Payment payment = Payment.builder()
                .merchantUid(merchantUid)
                .buyerName(buyerName)
                .buyerEmail(buyerEmail)
                .buyerTel(buyerTel)
                .amount(amount)
                .paymentDate(LocalDateTime.now())
                .build();

        return paymentRepository.save(payment);
    }

    // ✅ 결제 정보 조회
    public Payment getPaymentByMerchantUid(String merchantUid) {
        return paymentRepository.findByMerchantUid(merchantUid);
    }
}
