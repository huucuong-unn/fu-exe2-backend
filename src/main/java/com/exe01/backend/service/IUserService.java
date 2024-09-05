package com.exe01.backend.service;

import com.exe01.backend.dto.request.UserRequest;
import com.exe01.backend.dto.response.user.UserResponse;
import com.exe01.backend.entity.User;
import com.exe01.backend.exception.BaseException;

import java.util.UUID;

public interface IUserService {
    UserResponse create(UserRequest request) throws BaseException;

    UserResponse update(UUID id, UserRequest request) throws BaseException;

    User findById(UUID id) throws BaseException;
}
