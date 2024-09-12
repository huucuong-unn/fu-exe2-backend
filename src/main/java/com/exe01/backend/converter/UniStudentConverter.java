package com.exe01.backend.converter;

import com.exe01.backend.dto.request.UniStudentRequest;
import com.exe01.backend.dto.response.uniStudent.UniStudentResponse;
import com.exe01.backend.entity.UniStudent;

public class UniStudentConverter {
    public static UniStudentResponse fromEntityToUniStudentResponse(UniStudent uniStudent) {
        UniStudentResponse uniStudentResponse = new UniStudentResponse();
        uniStudentResponse.setId(uniStudent.getId());
        uniStudentResponse.setUserId(uniStudent.getUser().getId());
        uniStudentResponse.setRemainSubscription(uniStudent.getRemainSubscription());
        uniStudentResponse.setFullName(uniStudent.getFullName());
        uniStudentResponse.setUniversity(uniStudent.getUniversity());
        uniStudentResponse.setMajor(uniStudent.getMajor());
        uniStudentResponse.setCv(uniStudent.getCv());
        uniStudentResponse.setProfilePicture(uniStudent.getProfilePicture());

        return uniStudentResponse;
    }

    public static UniStudent fromRequestToEntity(UniStudentRequest request) {
        UniStudent uniStudent = new UniStudent();
        uniStudent.setRemainSubscription(request.getRemainSubscription());
        uniStudent.setFullName(request.getFullName());
        uniStudent.setUniversity(request.getUniversity());
        uniStudent.setMajor(request.getMajor());
        return uniStudent;
    }
}
