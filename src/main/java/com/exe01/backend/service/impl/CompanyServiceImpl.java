package com.exe01.backend.service.impl;

import com.exe01.backend.constant.ConstError;
import com.exe01.backend.constant.ConstHashKeyPrefix;
import com.exe01.backend.constant.ConstStatus;
import com.exe01.backend.converter.AccountConverter;
import com.exe01.backend.converter.CompanyConverter;
import com.exe01.backend.dto.CompanyDTO;
import com.exe01.backend.dto.Dashboard.TopFiveCompany;
import com.exe01.backend.dto.request.company.BaseCompanyRequest;
import com.exe01.backend.entity.Company;
import com.exe01.backend.enums.ErrorCode;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.models.PagingModel;
import com.exe01.backend.repository.CompanyRepository;
import com.exe01.backend.service.IAccountService;
import com.exe01.backend.service.ICompanyService;
import com.exe01.backend.validation.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompanyServiceImpl implements ICompanyService {

    Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    IAccountService accountService;

    @Override
    public CompanyDTO create(BaseCompanyRequest request) throws BaseException {
        try {
            logger.info("Create company");
            Company company = new Company();
            company.setName(request.getName());
            company.setCountry(request.getCountry());
            company.setAddress(request.getAddress());
            company.setCompany_website_url(request.getCompanyWebsiteUrl());
            company.setFacebook_url(request.getFacebookUrl());
            company.setDescription(request.getDescription());
            company.setWorkingTime(request.getWorkingTime());
            company.setCompanySize(request.getCompanySize());
            company.setCompanyType(request.getCompanyType());
            company.setStatus(ConstStatus.INACTIVE_STATUS);
            company.setAccount(AccountConverter.toEntity(accountService.findById(request.getAccountId())));
            company.setAvatarUrl(request.getAvatarUrlString());

            companyRepository.save(company);


            return CompanyConverter.toDto(company);

        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public Boolean update(UUID id, BaseCompanyRequest request) throws BaseException {
        try {
            logger.info("Update company");
            Company company = CompanyConverter.toEntity(findById(id));

            company.setId(id);
            company.setName(request.getName());
            company.setCountry(request.getCountry());
            company.setAddress(request.getAddress());
            company.setCompany_website_url(request.getCompanyWebsiteUrl());
            company.setFacebook_url(request.getFacebookUrl());
            company.setDescription(request.getDescription());
            company.setWorkingTime(request.getWorkingTime());
            company.setCompanySize(request.getCompanySize());
            company.setCompanyType(request.getCompanyType());

            companyRepository.save(company);

            return true;

        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException;
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public Boolean delete(UUID id) throws BaseException {
        return null;
    }

    @Override
    public Boolean changeStatus(UUID id) throws BaseException {
        try {
            logger.info("Change status company");
            Company company = CompanyConverter.toEntity(findById(id));

            if (company.getStatus().equals(ConstStatus.ACTIVE_STATUS)) {
                company.setStatus(ConstStatus.INACTIVE_STATUS);
            } else {
                company.setStatus(ConstStatus.ACTIVE_STATUS);
            }

            companyRepository.save(company);

            return true;

        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException;
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public PagingModel searchSortCompany(String name, String address, Integer page, Integer limit) throws BaseException {
        try {
            logger.info("Get all comapany with paging");
            PagingModel result = new PagingModel();
            result.setPage(page);
            Pageable pageable = PageRequest.of(page - 1, limit);

            String hashKeyForCompany = ConstHashKeyPrefix.HASH_KEY_PREFIX_FOR_COMPANY + "all:" + "searchBy:" + name + ":" + address + ":" + page + ":" + limit;

            List<CompanyDTO> companyDTOs;

                logger.info("Fetching company from database for page {} and limit {}", page, limit);
                List<Company> companies = companyRepository.searchAndSortCompanies(name, address, pageable);
                companyDTOs = companies.stream().map(CompanyConverter::toDto).toList();

            result.setListResult(companyDTOs);

            result.setTotalPage(((int) Math.ceil((double) (totalItemWithStatusActive()) / limit)));
            result.setTotalCount(totalItemWithStatusActive());
            result.setLimit(limit);

            return result;

        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public List<CompanyDTO> findAllByStatus() throws BaseException {
        try {
            logger.info("Get all company with no paging");

            String hashKeyForCompany = ConstHashKeyPrefix.HASH_KEY_PREFIX_FOR_COMPANY + "all:" + "active:";

            List<CompanyDTO> companyDTOs;

                logger.info("Fetching company from database ");
                List<Company> companies = companyRepository.findAllByStatus(ConstStatus.ACTIVE_STATUS);
                companyDTOs = companies.stream().map(CompanyConverter::toDto).toList();

            return companyDTOs;

        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public List<TopFiveCompany> getTopFiveCompany() throws BaseException {
//        try {
//            logger.info("Get top five company");
//            List<Object[]> topFiveCompany = companyRepository.topFiveCompanyWithMostApplication();
//            List<TopFiveCompany> topFiveCompanies = new ArrayList<>();
//            for (Object[] objects : topFiveCompany) {
//                TopFiveCompany topFiveCompany1 = new TopFiveCompany();
//                topFiveCompany1.setCompanyName((String) objects[0]);
//                topFiveCompany1.setApplicationCount((Long) objects[1]);
//                topFiveCompanies.add(topFiveCompany1);
//            }
//            return topFiveCompanies;
//        } catch (Exception baseException) {
//            if (baseException instanceof BaseException) {
//                throw baseException; // rethrow the original BaseException
//            }
//            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
//        }
        return null;
    }


    @Override
    public CompanyDTO findById(UUID id) throws BaseException {
        try {
            logger.info("Find company by id {}", id);
            String hashKeyForCompany = ConstHashKeyPrefix.HASH_KEY_PREFIX_FOR_COMPANY + id.toString();

            Optional<Company> companyById = companyRepository.findById(id);
            boolean isCompanyExist = companyById.isPresent();

            if (!isCompanyExist) {
                logger.warn("Company with id {} is not found", id);
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.Company.COMPANY_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
            }

            CompanyDTO companyDTO = CompanyConverter.toDto(companyById.get());

            return companyDTO;

        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException;
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public PagingModel getAll(Integer page, Integer limit) throws BaseException {
        try {
            logger.info("Get all comapany with paging");
            PagingModel result = new PagingModel();
            result.setPage(page);
            Pageable pageable = PageRequest.of(page - 1, limit);

            String hashKeyForCompany = ConstHashKeyPrefix.HASH_KEY_PREFIX_FOR_COMPANY + "all:" + page + ":" + limit;

            List<CompanyDTO> companyDTOs;

                logger.info("Fetching company from database for page {} and limit {}", page, limit);
                List<Company> companies = companyRepository.findAllByOrderByCreatedDate(pageable);
                companyDTOs = companies.stream().map(CompanyConverter::toDto).toList();

            result.setListResult(companyDTOs);

            result.setTotalPage(((int) Math.ceil((double) (totalItem()) / limit)));
            result.setLimit(limit);

            return result;

        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    private int totalItem() {
        return (int) companyRepository.count();
    }

    private int totalItemWithStatusActive() {
        return companyRepository.countByStatus(ConstStatus.ACTIVE_STATUS);
    }

    @Override
    public PagingModel findAllByStatusTrue(Integer page, Integer limit) throws BaseException {
        try {
            logger.info("Get all company with paging");
            PagingModel result = new PagingModel();
            result.setPage(page);
            Pageable pageable = PageRequest.of(page - 1, limit);

            String hashKeyForCompany = ConstHashKeyPrefix.HASH_KEY_PREFIX_FOR_COMPANY + "all:" + "active:" + page + ":" + limit;

            List<CompanyDTO> companyDTOs;

                logger.info("Fetching company from database for page {} and limit {}", page, limit);
                List<Company> companies = companyRepository.findAllByStatusOrderByCreatedDate(ConstStatus.ACTIVE_STATUS, pageable);
                companyDTOs = companies.stream().map(CompanyConverter::toDto).toList();

            result.setListResult(companyDTOs);

            result.setTotalPage(((int) Math.ceil((double) (totalItemWithStatusActive()) / limit)));
            result.setLimit(limit);

            return result;

        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

}
