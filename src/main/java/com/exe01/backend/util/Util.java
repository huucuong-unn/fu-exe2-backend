package com.exe01.backend.util;

import com.exe01.backend.bucket.BucketName;
import com.exe01.backend.enums.ErrorCode;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.fileStore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class Util {

    @Autowired
    FileStore fileStore;

    public String uploadImage(UUID id, MultipartFile file) throws BaseException {
        try {
            // 1. Check if image is not empty
            if (file.isEmpty()) {
                throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
            }
            // 2. If file is an image
            if (!Arrays.asList("image/jpeg", "image/png", "image/jpg").contains(file.getContentType())) {
                throw new IllegalStateException("File must be an image [ " + file.getContentType() + "]");
            }
            // 3. The user exists in our database
            // 4. Grab some metadata from file if any
            Map<String, String> metadata = new HashMap<>();
            metadata.put("Content-Type", file.getContentType());
            metadata.put("Content-Length", String.valueOf(file.getSize()));

            // 5. Store the image in S3 and update database (userProfileImageLink) with S3 image link
            String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), id);
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String filename = String.format("%s-%s%s", UUID.randomUUID(), originalFilename.substring(0, originalFilename.lastIndexOf('.')), extension);
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            return filename;
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw (BaseException) baseException;
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    public String uploadCvFile(UUID id, MultipartFile file) throws BaseException {
        try {
            // 1. Check if file is not empty
            if (file.isEmpty()) {
                throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
            }
            // 2. If file is a PDF
            if (!"application/pdf".equals(file.getContentType())) {
                throw new IllegalStateException("File must be a PDF [ " + file.getContentType() + "]");
            }
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

            return filename;
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw (BaseException) baseException;
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }

    }
}
