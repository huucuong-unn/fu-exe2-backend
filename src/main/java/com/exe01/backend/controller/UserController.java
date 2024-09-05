package com.exe01.backend.controller;

import com.exe01.backend.constant.ConstAPI;
import com.exe01.backend.dto.request.UserRequest;
import com.exe01.backend.dto.response.user.UserResponse;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@Tag(name = "User Controller")
@Slf4j
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping(value = ConstAPI.UserAPI.CREATE_USER)
    public UserResponse create(@RequestBody UserRequest request) throws BaseException {
        log.info("Creating new user with request: {}", request);
        return userService.create(request);
    }
}
