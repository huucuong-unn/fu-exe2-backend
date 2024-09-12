package com.exe01.backend.service;

import com.exe01.backend.dto.request.ApplicationRequest;
import com.exe01.backend.dto.request.BlogRequest;
import com.exe01.backend.dto.response.application.ApplicationResponse;
import com.exe01.backend.dto.response.blog.BlogResponse;
import com.exe01.backend.entity.Application;
import com.exe01.backend.entity.Blog;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.models.PagingModel;

import java.util.List;
import java.util.UUID;

public interface IBlogService {
    BlogResponse create(BlogRequest request) throws BaseException;

    BlogResponse update(UUID id, BlogRequest request) throws BaseException;

    Blog findById(UUID id) throws BaseException;

    List<BlogResponse> getOutStandingBlog() throws BaseException;

    PagingModel getBlogList(Integer page, Integer limit) throws BaseException;
}
