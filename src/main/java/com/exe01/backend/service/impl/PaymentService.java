package com.exe01.backend.service.impl;

import com.exe01.backend.constant.ConstError;
import com.exe01.backend.converter.PaymentConverter;
import com.exe01.backend.converter.UserConverter;
import com.exe01.backend.dto.request.transaction.PaymentRequest;
import com.exe01.backend.dto.response.payment.PaymentResponse;
import com.exe01.backend.dto.response.user.UserResponse;
import com.exe01.backend.entity.Payment;
import com.exe01.backend.entity.Subscription;
import com.exe01.backend.entity.User;
import com.exe01.backend.enums.ErrorCode;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.repository.PaymentRepository;
import com.exe01.backend.service.IPaymentService;
import com.exe01.backend.service.ISubscriptionService;
import com.exe01.backend.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    IUserService userService;

    @Autowired
    ISubscriptionService subscriptionService;

    @Autowired
    PaymentRepository paymentRepository;

    Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Override
    public Payment create(PaymentRequest request) throws BaseException {
      Payment payment = PaymentConverter.toEntity(request);
      User user = userService.findById(request.getUserId());
      Subscription subcription = subscriptionService.findById(request.getTierId());
        payment.setUser(user);
        payment.setTierName(subcription.getPlanType());
        paymentRepository.save(payment);
        return  payment;
    }

    @Override
    public void changeStatus(String status, UUID paymentId) throws BaseException {
        Payment payment =findById(paymentId);
        payment.setStatus(status);
        paymentRepository.save(payment);
    }

    @Override
    public Payment findById(UUID id) throws BaseException {
        Optional<Payment> payment = paymentRepository.findById(id);
        if(payment.isPresent()){
            return payment.get();
        }else{
            throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.Transaction.TRANSACTION_NOT_FOUND, ErrorCode.ERROR_500.getMessage());

        }
    }

    @Override
    public void UpdateRefId(UUID paymentId, Long refId) throws BaseException {
        Payment payment = findById(paymentId);
        payment.setRefId(refId);
        paymentRepository.save(payment);
    }

    @Override
    public Payment findByRefId(Long refId) throws BaseException {
        Optional<Payment> payment = paymentRepository.findByRefId(refId);
        if(payment.isPresent()){
            return payment.get();
        }else{
            throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.Transaction.TRANSACTION_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public List<PaymentResponse> getPayments() throws BaseException {
        try {
            logger.info("Get all payments");
            List<Payment> payments = paymentRepository.findAll();
            List<PaymentResponse> paymentResponses = new ArrayList<>();
            for (Payment u: payments) {
                UserResponse userResponse = UserConverter.toUserResponse(u.getUser());
                PaymentResponse paymentResponse = PaymentConverter.toPaymentResponse(u);
                paymentResponse.setUser(userResponse);
                paymentResponse.setEmail(u.getUser().getEmail());
                paymentResponses.add(paymentResponse);
            }

            return paymentResponses;
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.Transaction.TRANSACTION_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
        }

    }
}
