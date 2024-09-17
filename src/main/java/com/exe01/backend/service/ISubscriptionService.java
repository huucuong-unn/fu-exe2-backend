package com.exe01.backend.service;

import com.exe01.backend.dto.request.SubscriptionRequest;
import com.exe01.backend.dto.response.subscription.SubscriptionResponse;
import com.exe01.backend.entity.Subscription;
import com.exe01.backend.exception.BaseException;

import java.util.UUID;

public interface ISubscriptionService {
    SubscriptionResponse create(SubscriptionRequest request) throws BaseException;

    SubscriptionResponse update(UUID id, SubscriptionRequest request) throws BaseException;

    Subscription findById(UUID id) throws BaseException;

    Subscription findByPlanType(String planType) throws BaseException;
}
