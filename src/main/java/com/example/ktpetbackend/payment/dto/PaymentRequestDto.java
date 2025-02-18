package com.example.ktpetbackend.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDto {
    private String merchantUid;
    private String buyerName;
    private String buyerEmail;
    private String buyerTel;
    private Integer amount;
}
