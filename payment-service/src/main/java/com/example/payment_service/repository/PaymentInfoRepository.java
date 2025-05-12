package com.example.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.payment_service.model.PaymentInfo;


@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {
	PaymentInfo getByPaymentId(String paymentId);
}
