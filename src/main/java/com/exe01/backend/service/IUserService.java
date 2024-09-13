package com.exe01.backend.service;

import com.exe01.backend.dto.request.user.LoginRequest;
import com.exe01.backend.dto.request.user.UserRequest;
import com.exe01.backend.dto.response.user.UserResponse;
import com.exe01.backend.entity.User;
import com.exe01.backend.exception.BaseException;
import org.springframework.scheduling.annotation.Async;

import java.util.UUID;

public interface IUserService {
    UserResponse create(UserRequest request) throws BaseException;

    UserResponse update(UUID id, UserRequest request) throws BaseException;

    User findById(UUID id) throws BaseException;

    UserResponse login(UserRequest request) throws BaseException;

    UserResponse loginWithGoogle(LoginRequest request) throws BaseException;

    @Async
    void updateReviewCVTimes(UUID id, UUID subcriptionId) throws BaseException;

    void checkRemainReviewCV(UUID id) throws BaseException;

    Boolean changeStatus(UUID id, String status, String message) throws BaseException;

    User findByEmail(String email);
}
