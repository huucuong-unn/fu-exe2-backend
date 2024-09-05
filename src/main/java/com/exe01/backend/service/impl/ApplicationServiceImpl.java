package com.exe01.backend.service.impl;

import com.exe01.backend.bucket.BucketName;
import com.exe01.backend.constant.ConstError;
import com.exe01.backend.constant.ConstHashKeyPrefix;
import com.exe01.backend.constant.ConstStatus;
import com.exe01.backend.converter.ApplicationConverter;
import com.exe01.backend.converter.StudentConverter;
import com.exe01.backend.dto.ApplicationDTO;
import com.exe01.backend.dto.Dashboard.MonthlyApplication;
import com.exe01.backend.dto.request.PushNotificationRequest;
import com.exe01.backend.dto.request.application.BaseApplicationRequest;
import com.exe01.backend.entity.Account;
import com.exe01.backend.entity.Application;
import com.exe01.backend.entity.EmailDetailsEntity;
import com.exe01.backend.entity.Student;
import com.exe01.backend.enums.ErrorCode;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.fileStore.FileStore;
import com.exe01.backend.models.PagingModel;
import com.exe01.backend.repository.AccountRepository;
import com.exe01.backend.repository.ApplicationRepository;
import com.exe01.backend.service.IApplicationService;
import com.exe01.backend.service.IStudentService;
import com.exe01.backend.validation.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class ApplicationServiceImpl implements IApplicationService {

    Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Autowired
    ApplicationRepository applicationRepository;


    @Autowired
    IStudentService studentService;

    @Autowired
    FileStore fileStore;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    private PushNotificationService pushNotificationService;


    @Override
    public ApplicationDTO create(BaseApplicationRequest request) throws BaseException {
        try {
            logger.info("Create Application");

            Application application = new Application();
            Student student = StudentConverter.toEntity(studentService.findById(request.getStudentId()));
            Account account = accountRepository.findById(student.getAccount().getId()).orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), ConstError.Account.ACCOUNT_NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase()));

            application.setStudent(student);
            application.setStatus(ConstStatus.ApplicationStatus.PROCESSING);
            application.setEmail(request.getEmail());
            application.setIntroduce(request.getIntroduce());
            application.setFullName(request.getFullName());
            application.setPhoneNumber(request.getPhoneNumber());
            application.setFacebookUrl(request.getFacebookUrl());
            application.setReasonApply(request.getReasonApply());
            application.setZaloAccount(request.getZaloAccount());
            application.setUserAddress(request.getUserAddress());

            int points = student.getAccount().getPoint() - 10;
            if (points > 0) {
                account.setPoint(points);
            } else {
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.Account.NOT_HAVE_ENOUGH_POINT, ErrorCode.ERROR_500.getMessage());
            }

            applicationRepository.save(application);

            uploadCvFile(application.getId(), request.getCvFile());

            return ApplicationConverter.toDto(application);

        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public Boolean update(UUID id, BaseApplicationRequest request) throws BaseException {
        try {
            logger.info("Create Application with request {}", request);
            logger.info("Update application");

            Application application = ApplicationConverter.toEntity(findById(id));
            application.setStudent(StudentConverter.toEntity(studentService.findById(request.getStudentId())));
            application.setStatus(ConstStatus.ACTIVE_STATUS);
            application.setEmail(request.getEmail());
            application.setIntroduce(request.getIntroduce());
            //application.setCvFile(request.getCvFile());
            application.setFullName(request.getFullName());
            application.setPhoneNumber(request.getPhoneNumber());
            application.setFacebookUrl(request.getFacebookUrl());
            application.setReasonApply(request.getReasonApply());
            application.setZaloAccount(request.getZaloAccount());
            application.setUserAddress(request.getUserAddress());

            return true;

        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public Boolean changeStatus(UUID id) throws BaseException {
        try {
            logger.info("Change status application with id {}", id);
            Application application = ApplicationConverter.toEntity(findById(id));

            if (application.getStatus().equals(ConstStatus.ACTIVE_STATUS)) {
                application.setStatus(ConstStatus.INACTIVE_STATUS);
            } else {
                application.setStatus(ConstStatus.ACTIVE_STATUS);
            }

            applicationRepository.save(application);

            return true;
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException;
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public PagingModel findByMentorId(UUID mentorId, int page, int limit) throws BaseException {
        return null;
    }

    private int countByMentorIdAndStatus(UUID mentorId, String status) {
        return 0;
    }

    @Override
    public PagingModel findByMenteeId(UUID menteeId, int page, int limit) throws BaseException {
//        try {
//            logger.info("Get all Application");
//
//            PagingModel result = new PagingModel();
//            result.setPage(page);
//            Pageable pageable = PageRequest.of(page - 1, limit);
//
//            String hashKeyForApplication = ConstHashKeyPrefix.HASH_KEY_PREFIX_FOR_APPLICATION + menteeId + "all:" + page + ":" + limit;
//
//            List<ApplicationDTO> applicationDTOs = new ArrayList<>();
//
//            if (redisTemplate.opsForHash().hasKey(ConstHashKeyPrefix.HASH_KEY_PREFIX_FOR_APPLICATION, hashKeyForApplication)) {
//                logger.info("Fetching applications from cache for page {} and limit {}", page, limit);
//                applicationDTOs = (List<ApplicationDTO>) redisTemplate.opsForHash().get(ConstHashKeyPrefix.HASH_KEY_PREFIX_FOR_APPLICATION, hashKeyForApplication);
//            } else {
//                logger.info("Fetching applications from database for page {} and limit {}", page, limit);
//                List<Application> applications = applicationRepository.findByMentorId(menteeId, pageable);
//                applicationDTOs = applications.stream().map(ApplicationConverter::toDto).toList();
//                redisTemplate.opsForHash().put(ConstHashKeyPrefix.HASH_KEY_PREFIX_FOR_APPLICATION, hashKeyForApplication, applicationDTOs);
//            }
//
//            result.setListResult(applicationDTOs);
//
//            result.setTotalPage(((int) Math.ceil((double) (totalItemByStatusTrue()) / limit)));
//            result.setLimit(limit);
//
//            return result;
//
//        } catch (Exception baseException) {
//            if (baseException instanceof BaseException) {
//                throw baseException; // rethrow the original BaseException
//            }
//            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
//        }
        return null;
    }

    @Override
    public void approveApplication(UUID applicationId) throws BaseException {

        try {
            Application application = ApplicationConverter.toEntity(findById(applicationId));
            application.setStatus(ConstStatus.ApplicationStatus.APPROVED);

            //create mentee

            applicationRepository.save(application);

            String token = "fXprRsuuQNGzWDERlf5DwH:APA91bEcQLGQ45FTroZ98vSbwrZpj5OikY98nXTgbWj1Vn2hzAcQMW6SwOFjohoDV_T-UreMQI23AVr1CWaGGMJAN7DmoLE9ApLxsfiy7G01U-DcmkfjYdBhUbUKPu5tulpRgWpOSu6K";
            String title = "Application status";
            String body = "Your application has been approved";
            PushNotificationRequest pushNotificationRequest = new PushNotificationRequest(title, body, null, token);
            pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);


        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());

        }

    }

    @Override
    public void rejectApplication(UUID applicationId, String message) throws BaseException {

        try {
            Application application = ApplicationConverter.toEntity(findById(applicationId));
            application.setStatus(ConstStatus.ApplicationStatus.REJECTED);


            applicationRepository.save(application);
            EmailDetailsEntity emailDetailsEntity = new EmailDetailsEntity();
            emailDetailsEntity.setType("APPLICATION");
            emailDetailsEntity.setRecipient(application.getEmail());
            emailDetailsEntity.setMsgBody(message);
            emailDetailsEntity.setSubject("Application Rejected");
            emailService.sendSimpleMail(emailDetailsEntity);


        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());

        }

    }

    @Override
    public PagingModel findByMentorIdAndStatusAndSortByCreatedDate(UUID mentorId, String status, String createdDate, int page, int limit) throws BaseException {
        try {
            logger.info("Get all Application");

            PagingModel result = new PagingModel();
            result.setPage(page);
            Pageable pageable = PageRequest.of(page - 1, limit);

            String hashKeyForApplication = ConstHashKeyPrefix.HASH_KEY_PREFIX_FOR_APPLICATION + mentorId + "all:" + page + ":" + limit;

            List<ApplicationDTO> applicationDTOs = new ArrayList<>();

            logger.info("Fetching applications from database for page {} and limit {}", page, limit);
            List<Application> applications = null;
            applicationDTOs = applications.stream().map(ApplicationConverter::toDto).toList();

            result.setListResult(applicationDTOs);

            result.setTotalPage(((int) Math.ceil((double) (totalItemByStatusTrue()) / limit)));
            result.setLimit(limit);

            return result;

        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public PagingModel findByStudentIdAndStatusAndSort(UUID studentId, UUID companyId, String mentorName, String status, String createdDate, int page, int limit) throws BaseException {

        try {
            logger.info("Get all Application");

            PagingModel result = new PagingModel();
            result.setPage(page);
            Pageable pageable = PageRequest.of(page - 1, limit);

            String hashKeyForApplication = ConstHashKeyPrefix.HASH_KEY_PREFIX_FOR_APPLICATION + studentId + "all:" + companyId + status + createdDate + page + ":" + limit;

            List<ApplicationDTO> applicationDTOs = new ArrayList<>();

            logger.info("Fetching applications from database for page {} and limit {}", page, limit);
            List<Application> applications = applicationRepository.findByStudentIdAndSearchSort(studentId, mentorName, status, pageable);
            applicationDTOs = applications.stream().map(ApplicationConverter::toDto).toList();

            result.setListResult(applicationDTOs);

            result.setTotalCount(totalItem());
            result.setTotalPage(((int) Math.ceil((double) (totalItem()) / limit)));
            result.setLimit(limit);

            return result;

        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }

    }

    @Override
    public void uploadCvFile(UUID id, MultipartFile file) throws BaseException {
        try {
            // 1. Check if file is not empty
            if (file.isEmpty()) {
                throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
            }
            // 2. If file is a PDF
            if (!"application/pdf".equals(file.getContentType())) {
                throw new IllegalStateException("File must be a PDF [ " + file.getContentType() + "]");
            }
            // 3. The user exists in our database
            Application application = applicationRepository.findById(id).orElseThrow(() -> new IllegalStateException("Application not found"));
            // 4. Grab some metadata from file if any
            Map<String, String> metadata = new HashMap<>();
            metadata.put("Content-Type", file.getContentType());
            metadata.put("Content-Length", String.valueOf(file.getSize()));

            // 5. Store the PDF in S3 and update database (userDocumentLink) with S3 document link
            String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), id);
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String filename = String.format("%s-%s%s", UUID.randomUUID(), originalFilename.substring(0, originalFilename.lastIndexOf('.')), extension);
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            application.setCvFile(filename);

            applicationRepository.save(application);
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw (BaseException) baseException;
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }


    }

    @Override
    public List<MonthlyApplication> getApplicationByMonth() throws BaseException {
        try {
            logger.info("Get application by month");
            List<Object[]> applicationCountByMonth = applicationRepository.getApplicationCountByMonth();
            List<MonthlyApplication> monthlyApplications = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                MonthlyApplication monthlyApplication = new MonthlyApplication();
                monthlyApplication.setMonth(i);
                for (Object[] objects : applicationCountByMonth) {
                    if ((Integer) objects[0] == i) {
                        monthlyApplication.setApplicationCount((Long) objects[1]);
                        break;
                    }
                }
                monthlyApplications.add(monthlyApplication);
            }

            return monthlyApplications;
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public ApplicationDTO findById(UUID id) throws BaseException {
        try {
            logger.info("Find Application by id {}", id);
            Optional<Application> application = applicationRepository.findById(id);
            boolean isExist = application.isPresent();

            if (!isExist) {
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.Application.APPLICATION_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
            }

            ApplicationDTO applicationDTO = ApplicationConverter.toDto(application.get());

            return applicationDTO;
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public PagingModel getAll(Integer page, Integer limit) throws BaseException {
        try {
            logger.info("Get all Application with paging");
            PagingModel result = new PagingModel();
            result.setPage(page);
            Pageable pageable = PageRequest.of(page - 1, limit);

            String hashKeyForApplication = ConstHashKeyPrefix.HASH_KEY_PREFIX_FOR_APPLICATION + "all:" + page + ":" + limit;

            List<ApplicationDTO> applicationDTOs;
            logger.info("Fetching applications from database for page {} and limit {}", page, limit);
            List<Application> applications = applicationRepository.findAllByOrderByCreatedDate(pageable);
            applicationDTOs = applications.stream().map(ApplicationConverter::toDto).toList();


            result.setListResult(applicationDTOs);

            result.setTotalPage(((int) Math.ceil((double) (totalItem()) / limit)));
            result.setLimit(limit);

            return result;
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public PagingModel findAllByStatusTrue(Integer page, Integer limit) throws BaseException {
        try {

            logger.info("Get all Application with status active");

            PagingModel result = new PagingModel();
            result.setPage(page);
            Pageable pageable = PageRequest.of(page - 1, limit);

            String hashKeyForApplication = ConstHashKeyPrefix.HASH_KEY_PREFIX_FOR_APPLICATION + "all:" + "active:" + page + ":" + limit;

            List<ApplicationDTO> applicationDTOs = new ArrayList<>();

            logger.info("Fetching applications from database for page {} and limit {}", page, limit);
            List<Application> applications = applicationRepository.findAllByStatusOrderByCreatedDate(ConstStatus.ACTIVE_STATUS, pageable);
            applicationDTOs = applications.stream().map(ApplicationConverter::toDto).toList();
            result.setListResult(applicationDTOs);

            result.setTotalPage(((int) Math.ceil((double) (totalItemByStatusTrue()) / limit)));
            result.setLimit(limit);

            return result;

        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    public int totalItem() {
        return (int) applicationRepository.count();
    }

    public int totalItemByStatusTrue() {
        return applicationRepository.countByStatus(ConstStatus.ACTIVE_STATUS);
    }
}
