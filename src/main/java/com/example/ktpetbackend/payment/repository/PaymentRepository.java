package com.example.ktpetbackend.payment.repository;

import com.example.ktpetbackend.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByMerchantUid(String merchantUid);
}
