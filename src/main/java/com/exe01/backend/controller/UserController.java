package com.exe01.backend.controller;

import com.exe01.backend.constant.ConstAPI;
import com.exe01.backend.dto.request.user.LoginRequest;
import com.exe01.backend.dto.request.user.UserRequest;
import com.exe01.backend.dto.response.user.UserResponse;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@Tag(name = "User Controller")
@Slf4j
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping(value = ConstAPI.UserAPI.CREATE_USER,  consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse create(@ModelAttribute UserRequest request) throws BaseException {
        log.info("Creating new user with request: {}", request);
        return userService.create(request);
    }

    @PostMapping(value = ConstAPI.UserAPI.LOGIN, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse login(@RequestBody UserRequest request) throws BaseException {
        log.info("Login with request: {}", request);
        return userService.login(request);
    }

    @PostMapping(value = ConstAPI.UserAPI.LOGIN_GOOGLE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse loginWithGoogle(@RequestBody LoginRequest request) throws BaseException {
        log.info("Login with google with request: {}", request);
        return userService.loginWithGoogle(request);
    }

    @PutMapping(value = ConstAPI.UserAPI.CHANGE_STATUS + "{id}")
    public Boolean changeStatus(@PathVariable("id") UUID id, @RequestParam(value = "status", required = false) String status) throws BaseException {
        log.info("Change status by Id");
        return userService.changeStatus(id, status);
    }
}
