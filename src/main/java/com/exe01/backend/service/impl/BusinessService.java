package com.exe01.backend.service.impl;

import com.exe01.backend.constant.ConstError;
import com.exe01.backend.constant.ConstStatus;
import com.exe01.backend.converter.BusinessConverter;
import com.exe01.backend.dto.request.BusinessRequest;
import com.exe01.backend.dto.response.business.BusinessResponse;
import com.exe01.backend.dto.response.business.FeatureCompanyResponse;
import com.exe01.backend.entity.Business;
import com.exe01.backend.entity.User;
import com.exe01.backend.enums.ErrorCode;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.models.PagingModel;
import com.exe01.backend.repository.BusinessRepository;
import com.exe01.backend.service.IBusinessService;
import com.exe01.backend.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BusinessService implements IBusinessService {
    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private IUserService userService;
    Logger logger = LoggerFactory.getLogger(BusinessService.class);

    @Override
    public BusinessResponse create(BusinessRequest request) throws BaseException {
        try {
            logger.info("Create business");
            User userById = userService.findById(request.getUserId());
            Business business = BusinessConverter.fromRequestToEntity(request);
            business.setUser(userById);
            business.setStatus(ConstStatus.ACTIVE_STATUS);
            Business newBusiness = businessRepository.save(business);
            return BusinessConverter.fromEntityToBusinessResponse(newBusiness);
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public BusinessResponse update(UUID id, BusinessRequest request) throws BaseException {
        try {
            logger.info("Update business");
            Business businessById = findById(id);
            Business businessUpdate = businessRepository.save(businessById);
            return BusinessConverter.fromEntityToBusinessResponse(businessUpdate);
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public PagingModel getFeatureCompanies(Integer page, Integer limit) throws BaseException {
        try {
            logger.info("Get all feature company with paging");
            PagingModel result = new PagingModel();
            result.setPage(page);
            Pageable pageable = PageRequest.of(page - 1, limit);
            List<Business> featureCompanies = businessRepository.findAllByStatusOrderByCreatedDate(ConstStatus.ACTIVE_STATUS, pageable);
            List<FeatureCompanyResponse> featureCompanyResponses = featureCompanies.stream().map(BusinessConverter::fromEntityToFeatureCompanyResponse).toList();
            result.setListResult(featureCompanyResponses);
            result.setTotalPage(((int) Math.ceil((double) (totalItem()) / limit)));
            result.setLimit(limit);
            result.setTotalCount((int) businessRepository.count());
            return result;
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    public int totalItem() {
        return (int) businessRepository.count();
    }

    @Override
    public Business findById(UUID id) throws BaseException {
        try {
            logger.info("Find business by id");
            Optional<Business> businessById = businessRepository.findById(id);
            boolean isExist = businessById.isPresent();
            if (!isExist) {
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.Business.BUSINESS_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
            }

            return businessById.get();
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public void updateImage(UUID id, String logo, String background ) throws BaseException {
        try {
            logger.info("Update image for business");
            Business businessById = findById(id);
            businessById.setLogoPicture(logo);
            businessById.setBackgroundPicture(background);
            businessRepository.save(businessById);
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public Business findByName(String name) throws BaseException {
        try {
            logger.info("Find business by name");
            Business businessByName = businessRepository.findByName(name);
            if (businessByName == null) {
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.Business.BUSINESS_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
            }

            return businessByName;
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public Business findByUserId(UUID userId) throws BaseException {
        try {
            logger.info("Find business by user id");
            Business businessByUserId = businessRepository.findByUserId(userId);
            if (businessByUserId == null) {
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.Business.BUSINESS_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
            }

            return businessByUserId;
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }
}
