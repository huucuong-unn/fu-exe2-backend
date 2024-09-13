package com.exe01.backend.service;

import com.exe01.backend.dto.request.ApplicationRequest;
import com.exe01.backend.dto.response.application.ApplicationResponse;
import com.exe01.backend.entity.Application;
import com.exe01.backend.exception.BaseException;

import java.util.UUID;

public interface IApplicationService {
    ApplicationResponse create(ApplicationRequest request) throws BaseException;

    ApplicationResponse update(UUID id, ApplicationRequest request) throws BaseException;

    Application findById(UUID id) throws BaseException;

    void changeStatus(String status, UUID applicationId,String message) throws BaseException;
}
