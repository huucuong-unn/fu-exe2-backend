package com.exe01.backend.service.impl;

import com.exe01.backend.constant.ConstError;
import com.exe01.backend.converter.ApplicationConverter;
import com.exe01.backend.dto.request.ApplicationRequest;
import com.exe01.backend.dto.response.application.ApplicationResponse;
import com.exe01.backend.entity.Application;
import com.exe01.backend.entity.EmailDetailsEntity;
import com.exe01.backend.entity.InternshipProgram;
import com.exe01.backend.entity.UniStudent;
import com.exe01.backend.enums.ErrorCode;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.repository.ApplicationRepository;
import com.exe01.backend.service.IApplicationService;
import com.exe01.backend.service.IEmailService;
import com.exe01.backend.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ApplicationService implements IApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UniStudentService uniStudentService;

    @Autowired
    private Util util;

    @Autowired
    private InternshipProgramService internshipProgramService;

    @Autowired
    private IEmailService emailService;

    Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    @Override
    public ApplicationResponse create(ApplicationRequest request) throws BaseException {
        try {
            logger.info("Create application");
            UniStudent uniStudentById = uniStudentService.findById(request.getUniStudentId());
            InternshipProgram internshipProgramById = internshipProgramService.findById(request.getInternshipProgramId());
            Application application = ApplicationConverter.fromRequestToEntity(request);
            application.setInternshipProgram(internshipProgramById);
            application.setUniStudent(uniStudentById);
            application.setStatus("PENDING");
            String cvFile = util.uploadCvFile(UUID.randomUUID(), request.getCv());
            application.setCv(cvFile);
            Application newApplication = applicationRepository.save(application);
            newApplication.setCv(cvFile);
            return ApplicationConverter.fromEntityToApplicationResponse(newApplication);
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public ApplicationResponse update(UUID id, ApplicationRequest request) throws BaseException {
        return null;
    }

    @Override
    public Application findById(UUID id) throws BaseException {
        try {
            logger.info("Find application by id");
            Optional<Application> applicationById = applicationRepository.findById(id);
            boolean isExist = applicationById.isPresent();
            if (!isExist) {
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.Application.APPLICATION_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
            }

            return applicationById.get();
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public void changeStatus(String status, UUID applicationId, String message) throws BaseException {
        try {
            logger.info("Change status for application");
            Application applicationById = findById(applicationId);
            applicationById.setStatus(status);
            applicationRepository.save(applicationById);

            EmailDetailsEntity emailDetailsEntity = new EmailDetailsEntity();
            emailDetailsEntity.setRecipient(applicationById.getUniStudent().getUser().getEmail());
            emailDetailsEntity.setSubject("Application status");
            emailDetailsEntity.setMsgBody(message);
            emailDetailsEntity.setResult(status);
            emailDetailsEntity.setType("APPLICATION");

            emailService.sendSimpleMail(emailDetailsEntity);
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }
}
