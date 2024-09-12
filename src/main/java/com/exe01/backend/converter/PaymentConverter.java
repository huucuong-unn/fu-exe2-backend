package com.exe01.backend.converter;

import com.exe01.backend.dto.request.transaction.PaymentRequest;
import com.exe01.backend.entity.Payment;

public class PaymentConverter {
    public static Payment toEntity(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setTotal(request.getTotal());
        payment.setMethod(request.getMethod());
        payment.setStatus(request.getStatus());
        return payment;
    }
}
