package com.exe01.backend.service.impl;

import com.exe01.backend.constant.ConstError;
import com.exe01.backend.constant.ConstStatus;
import com.exe01.backend.converter.BlogConverter;
import com.exe01.backend.dto.request.BlogRequest;
import com.exe01.backend.dto.response.blog.BlogResponse;
import com.exe01.backend.entity.Blog;
import com.exe01.backend.entity.Business;
import com.exe01.backend.enums.ErrorCode;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.models.PagingModel;
import com.exe01.backend.repository.BlogRepository;
import com.exe01.backend.service.IBlogService;
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
public class BlogService implements IBlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BusinessService businessService;

    Logger logger = LoggerFactory.getLogger(BlogService.class);

    @Override
    public BlogResponse create(BlogRequest request) throws BaseException {
        try {
            logger.info("Create blog");
            Business businessById = businessService.findById(request.getBusinessId());
            Blog blog = BlogConverter.fromRequestToEntity(request);
            blog.setBusiness(businessById);
            blog.setStatus(ConstStatus.ACTIVE_STATUS);
            Blog newBlog = blogRepository.save(blog);

            return BlogConverter.fromEntityToBlogResponse(newBlog);
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public BlogResponse update(UUID id, BlogRequest request) throws BaseException {
        Blog blogById = findById(id);
        blogById.setPicture(request.getPicture());
        blogById.setContent(request.getContent());
        blogById.setTitleName(request.getTitleName());
        Blog updateBlog = blogRepository.save(blogById);

        return BlogConverter.fromEntityToBlogResponse(updateBlog);
    }

    @Override
    public Blog findById(UUID id) throws BaseException {
        try {
            logger.info("Find blog by id");
            Optional<Blog> blogById = blogRepository.findById(id);
            boolean isExist = blogById.isPresent();
            if (!isExist) {
                throw new BaseException(ErrorCode.ERROR_500.getCode(), ConstError.Blog.BLOG_NOT_FOUND, ErrorCode.ERROR_500.getMessage());
            }

            return blogById.get();
        } catch (Exception baseException) {
            if (baseException instanceof BaseException) {
                throw baseException; // rethrow the original BaseException
            }
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public List<BlogResponse> getOutStandingBlog() throws BaseException{
        try {
            logger.info("Get outstanding blog");
            Pageable limit = PageRequest.of(0, 3);
            List<Blog> blogs = blogRepository.getOutstandingBlog(limit);
            return blogs.stream().map(BlogConverter::fromEntityToBlogResponse).toList();
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    @Override
    public PagingModel getBlogList(Integer page, Integer limit) throws BaseException {
        try {
            logger.info("Get blog list");
            PagingModel result = new PagingModel();
            result.setPage(page);
            result.setLimit(limit);
            Pageable pageable = PageRequest.of(page - 1, limit);

            List<Blog> blogs = blogRepository.getBlogListStatusIsActive(pageable);
            result.setListResult(blogs.stream().map(BlogConverter::fromEntityToBlogResponse).toList());
            result.setTotalPage(((int) Math.ceil((double) (totalItem()) / limit)));
            result.setTotalCount(totalItem());
            return result;
        } catch (Exception baseException) {
            throw new BaseException(ErrorCode.ERROR_500.getCode(), baseException.getMessage(), ErrorCode.ERROR_500.getMessage());
        }
    }

    private int totalItem() {
        return blogRepository.countByStatus(ConstStatus.ACTIVE_STATUS);
    }
}
