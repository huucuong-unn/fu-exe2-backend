package com.exe01.backend.service.impl;

import com.exe01.backend.constant.ConstError;
import com.exe01.backend.converter.UniStudentConverter;
import com.exe01.backend.dto.request.UniStudentRequest;
import com.exe01.backend.dto.response.uniStudent.UniStudentResponse;
import com.exe01.backend.entity.Subscription;
import com.exe01.backend.entity.UniStudent;
import com.exe01.backend.entity.User;
import com.exe01.backend.enums.ErrorCode;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.repository.UniStudentRepository;
import com.exe01.backend.service.IUniStudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UniStudentService implements IUniStudentService {
    @Autowired
    private UniStudentRepository uniStudentRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UniStudentService.class);

    @Override
    public UniStudentResponse create(UniStudentRequest request) throws BaseException {
        try {
            logger.info("Create UniStudent");
            Subscription subscriptionById = null;
            if(request.getSubscriptionId() !=null){
                subscriptionById = subscriptionService.findById(request.getSubscriptionId());
            }
            User userById = userService.findById(request.getUserId());
            UniStudent uniStudent = UniStudentConverter.fromRequestToEntity(request);
            uniStudent.setSubscription(subscriptionById);
            uniStudent.setUser(userById);
            UniStudent newUniStudent = uniStudentRepository.save(uniStudent);

            return UniStudentConverter.fromEntityToUniStudentResponse(newUniStudent);
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public UniStudentResponse update(UUID id, UniStudentRequest request) throws BaseException {
        return null;
    }

    @Override
    public UniStudent findById(UUID id) throws BaseException {
        try {
            logger.info("Find uni student by id");
            Optional<UniStudent> uniStudentById = uniStudentRepository.findById(id);
            boolean isExist = uniStudentById.isPresent();
            if (!isExist) {
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.UniStudent.UNI_STUDENT_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
            }

            return uniStudentById.get();
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public void updateImage(UUID id, String imageUrl) throws BaseException {
        try {
            logger.info("Update image for uni student");
            UniStudent uniStudentById = findById(id);
            uniStudentById.setProfilePicture(imageUrl);
            uniStudentRepository.save(uniStudentById);
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public UniStudent findByUserId(UUID userId) throws BaseException {
        try {
            logger.info("Find uni student by user id");
            Optional<UniStudent> uniStudentByUserId = uniStudentRepository.findByUserId(userId);
            boolean isExist = uniStudentByUserId.isPresent();
            if (!isExist) {
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.UniStudent.UNI_STUDENT_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
            }

            return uniStudentByUserId.get();
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }
}
