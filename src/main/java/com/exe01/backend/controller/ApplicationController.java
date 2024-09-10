package com.exe01.backend.controller;

import com.exe01.backend.constant.ConstAPI;
import com.exe01.backend.dto.request.ApplicationRequest;
import com.exe01.backend.dto.response.application.ApplicationResponse;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.service.IApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@Tag(name = "Application Controller")
@Slf4j
public class ApplicationController {
    @Autowired
    private IApplicationService applicationService;

    @PostMapping(value = ConstAPI.ApplicationAPI.CREATE_APPLICATION)
    public ApplicationResponse create(@RequestBody ApplicationRequest request) throws BaseException {
        log.info("Creating new application with request: {}", request);
        return applicationService.create(request);
    }
}
