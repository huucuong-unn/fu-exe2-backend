package com.exe01.backend.service;

import com.exe01.backend.dto.request.InternshipProgramRequest;
import com.exe01.backend.dto.request.UserRequest;
import com.exe01.backend.dto.response.internshipProgram.InternshipProgramResponse;
import com.exe01.backend.dto.response.internshipProgram.Top3Response;
import com.exe01.backend.dto.response.user.UserResponse;
import com.exe01.backend.entity.InternshipProgram;
import com.exe01.backend.entity.User;
import com.exe01.backend.exception.BaseException;

import java.util.List;
import java.util.UUID;

public interface IInternshipProgramService {
    InternshipProgramResponse create(InternshipProgramRequest request) throws BaseException;

    InternshipProgramResponse update(UUID id, InternshipProgramRequest request) throws BaseException;

    InternshipProgram findById(UUID id) throws BaseException;

    List<Top3Response> findTop3InternshipProgram() throws BaseException;
}
