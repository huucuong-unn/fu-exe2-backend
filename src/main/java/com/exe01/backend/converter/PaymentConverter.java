package com.exe01.backend.converter;

import com.exe01.backend.dto.request.transaction.PaymentRequest;
import com.exe01.backend.dto.response.payment.PaymentResponse;
import com.exe01.backend.entity.Payment;

public class PaymentConverter {
    public static Payment toEntity(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setTotal(request.getTotal());
        payment.setMethod(request.getMethod());
        payment.setStatus(request.getStatus());
        return payment;
    }

    public static PaymentResponse toPaymentResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();

        response.setId(payment.getId());
        response.setRefId(payment.getRefId());
        response.setTotal(payment.getTotal());
        response.setTierName(payment.getTierName());
        response.setMethod(payment.getMethod());
        response.setCreatedDate(payment.getCreatedDate());
        response.setStatus(payment.getStatus());
        return response;
    }
}
