package com.exe01.backend.service;

import com.exe01.backend.dto.request.BusinessRequest;
import com.exe01.backend.dto.request.UniStudentRequest;
import com.exe01.backend.dto.response.business.BusinessResponse;
import com.exe01.backend.dto.response.uniStudent.UniStudentResponse;
import com.exe01.backend.entity.Business;
import com.exe01.backend.entity.UniStudent;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.models.PagingModel;

import java.util.UUID;

public interface IUniStudentService {
    UniStudentResponse create(UniStudentRequest request) throws BaseException;

    UniStudentResponse update(UUID id, UniStudentRequest request) throws BaseException;

    UniStudent findById(UUID id) throws BaseException;
}
