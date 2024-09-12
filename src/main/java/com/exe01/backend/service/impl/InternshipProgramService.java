package com.exe01.backend.service.impl;

import com.exe01.backend.constant.ConstError;
import com.exe01.backend.constant.ConstStatus;
import com.exe01.backend.converter.InternshipProgramConverter;
import com.exe01.backend.dto.request.InternshipProgramRequest;
import com.exe01.backend.dto.response.internshipProgram.InternshipProgramDetailResponse;
import com.exe01.backend.dto.response.internshipProgram.InternshipProgramResponse;
import com.exe01.backend.dto.response.internshipProgram.Top3Response;
import com.exe01.backend.entity.Business;
import com.exe01.backend.entity.InternshipProgram;
import com.exe01.backend.enums.ErrorCode;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.models.PagingModel;
import com.exe01.backend.repository.InternshipProgramRepository;
import com.exe01.backend.service.IBusinessService;
import com.exe01.backend.service.IInternshipProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
    public List<InternshipProgramResponse> getAllLimit4() throws BaseException{
        try {
            logger.info("Get 4 internship program");
            Pageable limit = PageRequest.of(0, 4);
            List<InternshipProgram> internshipPrograms = internshipProgramRepository.getAllInternshipProgramLimit4(limit);
            return internshipPrograms.stream().map(InternshipProgramConverter::fromEntityToInternshipProgramResponse).toList();
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public PagingModel getInternshipProgramsBySearchSort(Integer page, Integer limit, String keyword, String location) throws BaseException {
        try {
            logger.info("Get internship program list by search sort");
            PagingModel result = new PagingModel();
            result.setPage(page);
            result.setLimit(limit);
            Pageable pageable = PageRequest.of(page - 1, limit);
            List<InternshipProgram> internshipPrograms = internshipProgramRepository.getInternshipPrograms(keyword, location, pageable);
            result.setListResult(
                    internshipPrograms.stream().map(internshipProgram -> {
                try {
                    Business businessByName = businessService.findByName(internshipProgram.getBusiness().getName());
                    return new Top3Response(
                            internshipProgram.getId(),
                            internshipProgram.getTitleName(),
                            internshipProgram.getDescription(),
                            internshipProgram.getPicture(),
                            internshipProgram.getCreatedDate(),
                            businessByName.getLogoPicture(),
                            businessByName.getName(),
                            internshipProgram.getLocation(),
                            internshipProgram.getSkillsAndKeywordRelate()
                    );
                } catch (BaseException e) {
                    throw new RuntimeException(e);
                }
            }).toList());

            result.setTotalPage(((int) Math.ceil((double) (totalItem()) / limit)));
            result.setTotalCount(totalItem());

            return result;
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    private int totalItem() {
        return internshipProgramRepository.countByStatus(ConstStatus.InternshipStatus.INTERNSHIP_PROGRAM_OPEN);
    }

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
    public InternshipProgramDetailResponse getInternshipProgramDetailById(UUID id) throws BaseException {
        try {
            logger.info("Get Internship Program detail by id");
            Optional<InternshipProgram> internshipProgramById = internshipProgramRepository.findById(id);
            boolean isExist = internshipProgramById.isPresent();
            if (!isExist) {
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.InternshipProgram.INTERNSHIP_PROGRAM_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
            }

            Business businessById = businessService.findById(internshipProgramById.get().getBusiness().getId());
            if (Objects.isNull(businessById)) {
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.Business.BUSINESS_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
            }

            return InternshipProgramConverter.fromEntityToInternshipProgramDetailResponse(internshipProgramById.get(), businessById);
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
                            internshipProgram.getId(),
                            internshipProgram.getTitleName(),
                            internshipProgram.getDescription(),
                            internshipProgram.getPicture(),
                            internshipProgram.getCreatedDate(),
                            businessById.getLogoPicture(),
                            businessById.getName(),
                            internshipProgram.getLocation(),
                            internshipProgram.getSkillsAndKeywordRelate()
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
