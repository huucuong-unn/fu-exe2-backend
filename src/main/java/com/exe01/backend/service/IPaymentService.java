package com.exe01.backend.service;

import com.exe01.backend.dto.request.transaction.PaymentRequest;
import com.exe01.backend.dto.response.payment.PaymentDashBoardResponse;
import com.exe01.backend.dto.response.payment.PaymentResponse;
import com.exe01.backend.entity.Payment;
import com.exe01.backend.exception.BaseException;

import java.util.List;
import java.util.UUID;

public interface IPaymentService {
    Payment create(PaymentRequest request) throws BaseException;

    void changeStatus(String status, UUID paymentId) throws BaseException;

    Payment findById(UUID id) throws BaseException;

    void UpdateRefId(UUID paymentId, Long refId) throws BaseException;

    Payment findByRefId(Long refId) throws BaseException;

    List<PaymentResponse> getPayments() throws BaseException;

    PaymentDashBoardResponse getPaymentDashBoard() throws BaseException;
}
