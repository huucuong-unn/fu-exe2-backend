package com.exe01.backend.service.impl;

import com.exe01.backend.constant.ConstError;
import com.exe01.backend.constant.ConstStatus;
import com.exe01.backend.converter.UserConverter;
import com.exe01.backend.dto.request.UserRequest;
import com.exe01.backend.dto.response.user.UserResponse;
import com.exe01.backend.entity.User;
import com.exe01.backend.enums.ErrorCode;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.repository.UserRepository;
import com.exe01.backend.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserResponse create(UserRequest request) throws BaseException {
        try {
            logger.info("Create user");
            User user = UserConverter.fromRequestToEntity(request);
            user.setStatus(ConstStatus.ACTIVE_STATUS);
            User newUser = userRepository.save(user);
            return UserConverter.toUserResponse(newUser);
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public UserResponse update(UUID id, UserRequest request) throws BaseException{
        try {
            logger.info("Update user");
            User userById = findById(id);
            User userUpdate = userRepository.save(userById);
            return UserConverter.toUserResponse(userUpdate);
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public User findById(UUID id) throws BaseException {
        try {
            logger.info("Find user by id");
            Optional<User> userById = userRepository.findById(id);
            boolean isExist = userById.isPresent();
            if (!isExist) {
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.User.USER_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
            }

            return userById.get();
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }
}
