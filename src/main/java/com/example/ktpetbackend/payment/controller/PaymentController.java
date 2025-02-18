package com.example.ktpetbackend.payment.controller;

import com.example.ktpetbackend.payment.dto.PaymentRequestDto;
import com.example.ktpetbackend.payment.entity.Payment;
import com.example.ktpetbackend.payment.service.PaymentService;
import com.example.ktpetbackend.user.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // ✅ 결제 정보 저장 API
    @PostMapping("/complete")
    public ApiResponse<String> completePayment(@RequestBody PaymentRequestDto requestDto) {
        paymentService.savePayment(
                requestDto.getMerchantUid(),
                requestDto.getBuyerName(),
                requestDto.getBuyerEmail(),
                requestDto.getBuyerTel(),
                requestDto.getAmount()
        );

        return new ApiResponse<>("결제가 완료되었습니다.");
    }

    // ✅ 특정 결제 정보 조회 API
    @GetMapping("/{merchantUid}")
    public ApiResponse<Payment> getPaymentInfo(@PathVariable String merchantUid) {
        Payment payment = paymentService.getPaymentByMerchantUid(merchantUid);
        return new ApiResponse<>(payment);
    }
}
