package com.exe01.backend.controller;

import com.exe01.backend.constant.ConstAPI;
import com.exe01.backend.dto.request.BusinessRequest;
import com.exe01.backend.dto.response.business.BusinessResponse;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.models.PagingModel;
import com.exe01.backend.service.IBusinessService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Tag(name = "Business Controller")
@Slf4j
public class BusinessController {
    @Autowired
    private IBusinessService businessService;

    @PostMapping(value = ConstAPI.BusinessAPI.CREATE_BUSINESS)
    public BusinessResponse create(@RequestBody BusinessRequest request) throws BaseException {
        log.info("Creating new business with request: {}", request);
        return businessService.create(request);
    }

    @GetMapping(value = ConstAPI.BusinessAPI.GET_FEATURE_COMPANY)
    public PagingModel getFeatureCompanies(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "limit", required = false) Integer limit) throws BaseException {
        log.info("Getting all applications with page: {}, limit: {}", page, limit);
        return businessService.getFeatureCompanies(page, limit);
    }
}
