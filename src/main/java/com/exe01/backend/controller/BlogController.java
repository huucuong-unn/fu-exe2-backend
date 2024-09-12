package com.exe01.backend.controller;

import com.exe01.backend.constant.ConstAPI;
import com.exe01.backend.dto.request.ApplicationRequest;
import com.exe01.backend.dto.request.BlogRequest;
import com.exe01.backend.dto.response.application.ApplicationResponse;
import com.exe01.backend.dto.response.blog.BlogResponse;
import com.exe01.backend.dto.response.internshipProgram.Top3Response;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.models.PagingModel;
import com.exe01.backend.service.IBlogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@Tag(name = "Blog Controller")
@Slf4j
public class BlogController {
    @Autowired
    private IBlogService blogService;

    @PostMapping(value = ConstAPI.BlogAPI.CREATE_BLOG)
    public BlogResponse create(@RequestBody BlogRequest request) throws BaseException {
        log.info("Creating new blog with request: {}", request);
        return blogService.create(request);
    }

    @PutMapping(value = ConstAPI.BlogAPI.UPDATE_BLOG + "{id}")
    public BlogResponse update(@PathVariable("id") UUID id, @RequestBody BlogRequest request) throws BaseException {
        log.info("Creating new blog with request: {}", request);
        return blogService.update(id, request);
    }

    @GetMapping(value = ConstAPI.BlogAPI.GET_OUTSTANDING_BLOG)
    public List<BlogResponse> getOutstandingBlog() throws BaseException {
        log.info("Getting outstanding blog");
        return blogService.getOutStandingBlog();
    }

    @GetMapping(value = ConstAPI.BlogAPI.GET_BLOGS)
    public PagingModel getBlogs(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "limit", required = false) Integer limit) throws BaseException {
        log.info("Getting blogs");
        return blogService.getBlogList(page, limit);
    }
}
