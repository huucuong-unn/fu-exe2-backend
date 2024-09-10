package com.exe01.backend.service.impl;

import com.exe01.backend.constant.ConstError;
import com.exe01.backend.converter.SubscriptionConverter;
import com.exe01.backend.dto.request.SubscriptionRequest;
import com.exe01.backend.dto.response.subscription.SubscriptionResponse;
import com.exe01.backend.entity.Business;
import com.exe01.backend.entity.Subscription;
import com.exe01.backend.enums.ErrorCode;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.repository.SubscriptionRepository;
import com.exe01.backend.service.ISubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SubscriptionService implements ISubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    @Override
    public SubscriptionResponse create(SubscriptionRequest request) throws BaseException {
        try {
            logger.info("Create subscription");
            Subscription subscription = SubscriptionConverter.fromRequestToEntity(request);
            Subscription newSubscription = subscriptionRepository.save(subscription);
            return SubscriptionConverter.fromEntityToSubscriptionResponse(newSubscription);
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public SubscriptionResponse update(UUID id, SubscriptionRequest request) throws BaseException {
        return null;
    }

    @Override
    public Subscription findById(UUID id) throws BaseException {
        try {
            logger.info("Find subscription by id");
            Optional<Subscription> subscriptionById = subscriptionRepository.findById(id);
            boolean isExist = subscriptionById.isPresent();
            if (!isExist) {
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.Subscription.SUBSCRIPTION_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
            }

            return subscriptionById.get();
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }
}
