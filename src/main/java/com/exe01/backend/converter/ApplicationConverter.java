package com.exe01.backend.converter;

import com.exe01.backend.dto.request.ApplicationRequest;
import com.exe01.backend.dto.response.application.ApplicationResponse;
import com.exe01.backend.entity.Application;

public class ApplicationConverter {
    public static ApplicationResponse fromEntityToApplicationResponse(Application application) {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setId(application.getId());
        applicationResponse.setInternshipProgramId(application.getInternshipProgram().getId());
        applicationResponse.setUniStudentId(application.getUniStudent().getId());
        applicationResponse.setCv(application.getCv());
        applicationResponse.setStatus(application.getStatus());

        return applicationResponse;
    }

    public static Application fromRequestToEntity(ApplicationRequest request) {
        Application application = new Application();
        application.setStatus(request.getStatus());
        return application;
    }
}
