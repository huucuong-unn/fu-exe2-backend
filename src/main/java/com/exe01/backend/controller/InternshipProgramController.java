package com.exe01.backend.controller;

import com.exe01.backend.constant.ConstAPI;
import com.exe01.backend.dto.request.InternshipProgramRequest;
import com.exe01.backend.dto.response.internshipProgram.InternshipProgramDetailResponse;
import com.exe01.backend.dto.response.internshipProgram.InternshipProgramResponse;
import com.exe01.backend.dto.response.internshipProgram.Top3Response;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.models.PagingModel;
import com.exe01.backend.service.IInternshipProgramService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@Tag(name = "Internship Program Controller")
@Slf4j
public class InternshipProgramController {
    @Autowired
    private IInternshipProgramService internshipProgramService;

    @PostMapping(value = ConstAPI.InternshipProgram.CREATE_INTERNSHIP_PROGRAM)
    public InternshipProgramResponse create(@RequestBody InternshipProgramRequest request) throws BaseException {
        log.info("Creating new internship program with request: {}", request);
        return internshipProgramService.create(request);
    }

    @GetMapping(value = ConstAPI.InternshipProgram.TOP_3_INTERNSHIP_PROGRAM)
    public List<Top3Response> getTop3InternshipProgram() throws BaseException {
        log.info("Getting top 3 internship program in month");
        return internshipProgramService.findTop3InternshipProgram();
    }

    @GetMapping(value = ConstAPI.InternshipProgram.LIMIT_4_INTERNSHIP_PROGRAM)
    public List<InternshipProgramResponse> get4Limit() throws BaseException {
        log.info("Getting 4 internship program");
        return internshipProgramService.getAllLimit4();
    }

    @GetMapping(value = ConstAPI.InternshipProgram.GET_INTERNSHIP_PROGRAMS_SEARCH_SORT)
    public PagingModel getInternshipProgramsSearchSort(@RequestParam(value = "page", required = false) Integer page
                                                    , @RequestParam(value = "limit", required = false) Integer limit
                                                    , @RequestParam(value = "keyword", required = false) String keyword
                                                    , @RequestParam(value = "location", required = false) String location) throws BaseException {
        log.info("Getting internship program list by search or sort");
        return internshipProgramService.getInternshipProgramsBySearchSort(page, limit, keyword, location);
    }

    @GetMapping(value = ConstAPI.InternshipProgram.GET_INTERNSHIP_PROGRAMS_DETAIL + "{id}")
    public InternshipProgramDetailResponse getInternshipProgramDetailById(@PathVariable("id") UUID id) throws BaseException {
        log.info("Getting internship program detail by id");
        return internshipProgramService.getInternshipProgramDetailById(id);
    }
}
