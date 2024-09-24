package com.exe01.backend.controller;

import com.exe01.backend.constant.ConstAPI;
import com.exe01.backend.dto.request.SubscriptionRequest;
import com.exe01.backend.dto.response.subscription.SubscriptionResponse;
import com.exe01.backend.entity.Subscription;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.service.ISubscriptionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@Tag(name = "Subscription Controller")
@Slf4j
public class SubscriptionController {
    @Autowired
    private ISubscriptionService subscriptionService;

    @PostMapping(value = ConstAPI.SubscriptionAPI.CREATE_SUBSCRIPTION)
    public SubscriptionResponse create(@RequestBody SubscriptionRequest request) throws BaseException {
        log.info("Creating new subscription with request: {}", request);
        return subscriptionService.create(request);
    }

    @GetMapping(value = ConstAPI.SubscriptionAPI.GET_SUBSCRIPTION_BY_USER_ID + "{id}")
    public SubscriptionResponse findByUserId(@PathVariable("id") UUID id) throws BaseException {
        log.info("Getting subscription by id: {}", id);
        return subscriptionService.findByUserId(id);
    }
}
