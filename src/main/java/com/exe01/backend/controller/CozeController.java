package com.exe01.backend.controller;

import com.exe01.backend.constant.ConstAPI;
import com.exe01.backend.dto.request.coze.CozeCreateCoverLetterRequest;
import com.exe01.backend.dto.request.coze.CozeReviewCVRequest;
import com.exe01.backend.dto.response.coze.CozeCreateCoverLetterResponse;
import com.exe01.backend.dto.response.coze.CozeFeedbackResponse;
import com.exe01.backend.dto.response.coze.CozeUploadFileResponse;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.service.ICozeService;
import com.exe01.backend.service.impl.CozeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class CozeController {

    @Autowired
    CozeService cozeService;

    @PostMapping(value = ConstAPI.CozeAPI.UPLOAD_FILE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public CozeFeedbackResponse uploadFile(@ModelAttribute("file")CozeReviewCVRequest request) throws BaseException {
        // Here you can call the service to upload the file to the external API (e.g., Coze API)
        // Assuming you have a service named `cozeService` and a method `uploadFile`
       return cozeService.uploadFile(request.getFile(), request.getUserId());
        // Handle response from the service
    }

    @PostMapping(value = ConstAPI.CozeAPI.CREATE_COVER_LETTER)
    public CozeCreateCoverLetterResponse createCoverLetter(@RequestBody CozeCreateCoverLetterRequest request) throws BaseException {
        return cozeService.CreateCoverLeter(request);
    }





}
