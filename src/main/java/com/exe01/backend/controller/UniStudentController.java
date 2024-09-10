package com.exe01.backend.controller;

import com.exe01.backend.constant.ConstAPI;
import com.exe01.backend.dto.request.BusinessRequest;
import com.exe01.backend.dto.request.UniStudentRequest;
import com.exe01.backend.dto.response.business.BusinessResponse;
import com.exe01.backend.dto.response.uniStudent.UniStudentResponse;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.service.IUniStudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@Tag(name = "Uni Student Controller")
@Slf4j
public class UniStudentController {
    @Autowired
    private IUniStudentService uniStudentService;

    @PostMapping(value = ConstAPI.UniStudentAPI.CREATE_UNI_STUDENT)
    public UniStudentResponse create(@RequestBody UniStudentRequest request) throws BaseException {
        log.info("Creating new uni student with request: {}", request);
        return uniStudentService.create(request);
    }
}
