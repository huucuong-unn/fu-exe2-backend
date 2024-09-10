package com.exe01.backend.service.impl;

import com.exe01.backend.constant.ConstError;
import com.exe01.backend.constant.ConstStatus;
import com.exe01.backend.converter.InternshipProgramConverter;
import com.exe01.backend.dto.request.InternshipProgramRequest;
import com.exe01.backend.dto.response.internshipProgram.InternshipProgramResponse;
import com.exe01.backend.dto.response.internshipProgram.Top3Response;
import com.exe01.backend.entity.Business;
import com.exe01.backend.entity.InternshipProgram;
import com.exe01.backend.enums.ErrorCode;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.repository.InternshipProgramRepository;
import com.exe01.backend.service.IBusinessService;
import com.exe01.backend.service.IInternshipProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InternshipProgramService implements IInternshipProgramService {
    @Autowired
    private InternshipProgramRepository internshipProgramRepository;

    @Autowired
    private IBusinessService businessService;

    Logger logger = LoggerFactory.getLogger(InternshipProgramService.class);

    @Override
    public InternshipProgramResponse create(InternshipProgramRequest request) throws BaseException {
        try {
            logger.info("Create internship program");
            Business businessById = businessService.findById(request.getBusinessId());
            InternshipProgram internshipProgram = InternshipProgramConverter.fromRequestToEntity(request);
            internshipProgram.setBusiness(businessById);
            internshipProgram.setStatus(ConstStatus.INACTIVE_STATUS);
            InternshipProgram newInternshipProgram = internshipProgramRepository.save(internshipProgram);
            return InternshipProgramConverter.fromEntityToInternshipProgramResponse(newInternshipProgram);
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public InternshipProgramResponse update(UUID id, InternshipProgramRequest request) throws BaseException {
        return null;
    }

    @Override
    public InternshipProgram findById(UUID id) throws BaseException {
        try {
            logger.info("Find internship program by id");
            Optional<InternshipProgram> internshipProgramById = internshipProgramRepository.findById(id);
            boolean isExist = internshipProgramById.isPresent();
            if (!isExist) {
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.InternshipProgram.INTERNSHIP_PROGRAM_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
            }

            return internshipProgramById.get();
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public List<Top3Response> findTop3InternshipProgram() throws BaseException{
        try {
            logger.info("Find top 3 internship program in month");
            List<InternshipProgram> top3InternshipProgramEntities = internshipProgramRepository.findTop3InternshipProgramsByApplications();
            return top3InternshipProgramEntities.stream().map(internshipProgram -> {
                try {
                    Business businessById = businessService.findById(internshipProgram.getBusiness().getId());
                    return new Top3Response(
                            internshipProgram.getTitleName(),
                            internshipProgram.getDescription(),
                            internshipProgram.getPicture(),
                            internshipProgram.getCreatedDate(),
                            businessById.getLogoPicture(),
                            businessById.getName(),
                            internshipProgram.getLocation()
                    );
                } catch (BaseException e) {
                    throw new RuntimeException(e);
                }
            }).toList();
        } catch (Exception baseException){
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }
}
