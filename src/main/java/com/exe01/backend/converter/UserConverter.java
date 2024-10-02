package com.exe01.backend.converter;

import com.exe01.backend.dto.request.user.UserRequest;
import com.exe01.backend.dto.response.user.UserResponse;
import com.exe01.backend.entity.User;

public class UserConverter {
    public static UserResponse toUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setDob(user.getDob());
        userResponse.setRemainReviewCVTimes(user.getRemainReviewCVTimes());
        userResponse.setRemainInterviewTimes(user.getRemainInterviewTimes());
        userResponse.setStartDateSubscription(user.getStartDateSubscription());
        userResponse.setExpiryDateSubscription(user.getExpiryDateSubscription());
        userResponse.setRole(user.getRole());
        userResponse.setStatus(user.getStatus());

        return userResponse;
    }

    public static User fromRequestToEntity(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setDob(request.getDob());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        return user;
    }
}
