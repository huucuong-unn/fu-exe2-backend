package com.exe01.backend.service.impl;

import com.exe01.backend.constant.Const;
import com.exe01.backend.constant.ConstError;
import com.exe01.backend.constant.ConstStatus;
import com.exe01.backend.converter.UserConverter;
import com.exe01.backend.dto.request.BusinessRequest;
import com.exe01.backend.dto.request.UniStudentRequest;
import com.exe01.backend.dto.request.user.LoginRequest;
import com.exe01.backend.dto.request.user.UserRequest;
import com.exe01.backend.dto.response.business.BusinessResponse;
import com.exe01.backend.dto.response.uniStudent.UniStudentResponse;
import com.exe01.backend.dto.response.user.UserResponse;
import com.exe01.backend.entity.*;
import com.exe01.backend.enums.ErrorCode;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.repository.UserRepository;
import com.exe01.backend.service.IEmailService;
import com.exe01.backend.service.ISubscriptionService;
import com.exe01.backend.service.IUniStudentService;
import com.exe01.backend.service.IUserService;
import com.exe01.backend.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private IUniStudentService uniStudentService;

    @Autowired
    @Lazy
    private BusinessService businessService;

    @Autowired
    @Lazy
    private ISubscriptionService subscriptionService;

    @Autowired
    @Lazy
    private IEmailService emailService;

    @Autowired
    private Util util;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    @Transactional
    public UserResponse create(UserRequest request) throws BaseException {
        try {
            logger.info("Create user");
            String imgId = "";
            String imgId2;
            CheckEmailExistence(request.getEmail());
            User user = UserConverter.fromRequestToEntity(request);
            Subscription subscription = subscriptionService.findById(request.getSubscriptionId());
            user.setStatus(ConstStatus.PENDING);
            user.setSubscription(subscription);
            user.setRemainReviewCVTimes(1);
            user.setRemainInterviewTimes(1);
            User newUser = userRepository.save(user);

            // check role to create student or business
            switch (user.getRole()) {
                case "student":
                    UniStudentRequest uniStudentRequest = request.getUniStudentRequest();
                    // set userId for uniStudent
                    uniStudentRequest.setUserId(newUser.getId());
                    // create uniStudent
                    UniStudentResponse uniStudent = uniStudentService.create(uniStudentRequest);
                    // upload image
                    imgId = util.uploadImage(uniStudent.getId(), request.getUniStudentRequest().getProfilePicture());
                    // update imageId for uniStudent
                    uniStudentService.updateImage(uniStudent.getId(), imgId);
                    break;
                case "business":
                    BusinessRequest businessRequest = request.getBusinessRequest();
                    // set userId for business
                    businessRequest.setUserId(newUser.getId());
                    // create business
                    BusinessResponse businessResponse = businessService.create(businessRequest);
                    // upload image
                    imgId = util.uploadImage(businessResponse.getId(), request.getBusinessRequest().getLogoPicture());
                    imgId2 = util.uploadImage(businessResponse.getId(), request.getBusinessRequest().getBackgroundPicture());
                    // update imageId for business
                    businessService.updateImage(businessResponse.getId(), imgId, imgId2);
                    break;
                case "admin":
                    break;
                default:
                    throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.User.INVALID_ROLE, ErrorCode.ERROR_500.getMessage());
            }
            UserResponse userResponse = UserConverter.toUserResponse(newUser);
            userResponse.setPictureUrl(imgId);
            return userResponse;
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    private boolean CheckEmailExistence(String email) throws BaseException {
        Optional<User> userByEmail = userRepository.findByEmail(email);
        if (userByEmail.isPresent()) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.User.EMAIL_EXISTED, ErrorCode.ERROR_500.getMessage());
        }
        return true;
    }

    @Override
    public UserResponse update(UUID id, UserRequest request) throws BaseException {
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

    @Override
    public UserResponse login(UserRequest request) throws BaseException {
        try {
            logger.info("Find user by email and password");
            Optional<User> userByEmailAndPassword = userRepository.findByEmailAndPasswordAndRole(
                    request.getEmail(),
                    request.getPassword(),
                    request.getRole()
            );
            if (userByEmailAndPassword == null) {
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.User.USER_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
            }
            UserResponse userResponse = UserConverter.toUserResponse(userByEmailAndPassword.get());
            if(request.getRole().equals("student")) {
                UniStudent uniStudent = uniStudentService.findByUserId(userByEmailAndPassword.get().getId());
                userResponse.setPictureUrl(uniStudent.getProfilePicture());
            }
            return userResponse;
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public UserResponse loginWithGoogle(LoginRequest request) throws BaseException {
        try {
            logger.info("Find user by email and role");
            Optional<User> userByEmailAndPassword = userRepository.findByEmailAndRole(
                    request.getEmail(),
                    request.getRole()
            );
            if (!userByEmailAndPassword.isPresent()) {
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.User.USER_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
            }
            UserResponse userResponse = UserConverter.toUserResponse(userByEmailAndPassword.get());

            switch (request.getRole()) {
                case "student":
                    UniStudent uniStudent = uniStudentService.findByUserId(userByEmailAndPassword.get().getId());
                    userResponse.setPictureUrl(uniStudent.getProfilePicture());
                    break;
                case "business": {
                    Business businessResponse = businessService.findByUserId(userByEmailAndPassword.get().getId());
                    userResponse.setPictureUrl(businessResponse.getLogoPicture());
                }
                default:
                    break;
            }
            return userResponse;
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public UserResponse findByIdV2(UUID id) throws BaseException {
        try {
            logger.info("Find user by id");
            Optional<User> userById = userRepository.findById(id);
            boolean isExist = userById.isPresent();
            if (!isExist) {
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.User.USER_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
            }

            UserResponse userResponse = UserConverter.toUserResponse(userById.get());
                    UniStudent uniStudent = uniStudentService.findByUserId(userById.get().getId());
                    userResponse.setPictureUrl(uniStudent.getProfilePicture());
            return userResponse;
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    @Async
    public void updateReviewCVTimes(UUID id, UUID subcriptionId,String type) throws BaseException {
        try {
            logger.info("Update review CV times");
            User userById = findById(id);
            if (subcriptionId != null) {
                Subscription subscription = subscriptionService.findById(subcriptionId);
                userById.setRemainReviewCVTimes(userById.getRemainReviewCVTimes() + subscription.getReviewCVTime());
                userById.setRemainInterviewTimes(userById.getRemainInterviewTimes() + subscription.getInterviewTime());
                userById.setSubscription(subscription);
                Date currentDate = new Date();
                userById.setStartDateSubscription(currentDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DAY_OF_MONTH, 30);

                Date expiryDate = calendar.getTime();
                userById.setExpiryDateSubscription(expiryDate);
            } else {
                switch (type){
                    case Const.COVER_LETTER:
                        userById.setRemainInterviewTimes(userById.getRemainInterviewTimes() - 1);
                        break;
                    case Const.CV:
                        userById.setRemainReviewCVTimes(userById.getRemainReviewCVTimes() - 1);
                        break;
                }
            }
            userRepository.save(userById);
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public void checkRemainReviewCV(UUID id,String type) throws BaseException {
        User userById = findById(id);
        switch (type){
            case Const.COVER_LETTER:
                if (userById.getRemainInterviewTimes() <=0) {
                    throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.Coze.OUT_OF_TIMES, ErrorCode.ERROR_500.getMessage());
                }
                break;
            case Const.CV:
                if (userById.getRemainReviewCVTimes() <= 0) {
                    throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.Coze.OUT_OF_TIMES, ErrorCode.ERROR_500.getMessage());
                }
                break;
        }
    }

    @Override
    public Boolean changeStatus(UUID id, String status, String message) throws BaseException {
        try {
            User userById = findById(id);
            userById.setStatus(status);
            userRepository.save(userById);

            EmailDetailsEntity emailDetailsEntity = new EmailDetailsEntity();
            emailDetailsEntity.setRecipient(userById.getEmail());
            emailDetailsEntity.setSubject("Account Status");
            emailDetailsEntity.setMsgBody(message);
            emailDetailsEntity.setResult(status);
            emailDetailsEntity.setType("ACCOUNT");

            emailService.sendSimpleMail(emailDetailsEntity);

            return true;
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public boolean isUserSubscriptionActive(UUID userId, Date currentDate) {
        Optional<User> user = userRepository.checkUserSubscription(userId, currentDate);
        return user.isPresent();
    }

    @Override
    public List<UserResponse> getUsers() throws BaseException {
        try {
            logger.info("Find all users ");
            List<User> users = userRepository.findAll();
            List<UserResponse> userResponses = new ArrayList<>();
            for (User u: users) {
                UserResponse userResponse = UserConverter.toUserResponse(u);
                userResponses.add(userResponse);
            }

            return userResponses;
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }
}
