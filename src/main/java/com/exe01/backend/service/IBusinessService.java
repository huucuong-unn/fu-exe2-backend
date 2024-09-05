package com.exe01.backend.service;

import com.exe01.backend.dto.request.BusinessRequest;
import com.exe01.backend.dto.response.business.BusinessResponse;
import com.exe01.backend.entity.Business;
import com.exe01.backend.exception.BaseException;

import java.util.UUID;

public interface IBusinessService {
    BusinessResponse create(BusinessRequest request) throws BaseException;

    BusinessResponse update(UUID id, BusinessRequest request) throws BaseException;

    Business findById(UUID id) throws BaseException;
}
