package com.exe01.backend.converter;

import com.exe01.backend.dto.request.SubscriptionRequest;
import com.exe01.backend.dto.response.business.BusinessResponse;
import com.exe01.backend.dto.response.subscription.SubscriptionResponse;
import com.exe01.backend.entity.Business;
import com.exe01.backend.entity.Subscription;

public class SubscriptionConverter {
    public static SubscriptionResponse fromEntityToSubscriptionResponse(Subscription subscription) {
        SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
        subscriptionResponse.setId(subscription.getId());
        subscriptionResponse.setPlanType(subscription.getPlanType());
        subscriptionResponse.setStartDate(subscription.getStartDate());
        subscriptionResponse.setEndDate(subscription.getEndDate());

        return subscriptionResponse;
    }

    public static Subscription fromRequestToEntity(SubscriptionRequest request) {
        Subscription subscription = new Subscription();
        subscription.setPlanType(request.getPlanType());
        subscription.setStartDate(request.getStartDate());
        subscription.setEndDate(request.getEndDate());

        return subscription;
    }
}
