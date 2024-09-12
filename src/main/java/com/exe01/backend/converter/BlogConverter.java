package com.exe01.backend.converter;

import com.exe01.backend.dto.request.BlogRequest;
import com.exe01.backend.dto.response.blog.BlogResponse;
import com.exe01.backend.entity.Blog;

public class BlogConverter {
    public static BlogResponse fromEntityToBlogResponse(Blog blog) {
        BlogResponse blogResponse = new BlogResponse();
        blogResponse.setId(blog.getId());
        blogResponse.setBusinessId(blog.getBusiness().getId());
        blogResponse.setTitleName(blog.getTitleName());
        blogResponse.setPicture(blog.getPicture());
        blogResponse.setContent(blog.getContent());
        blog.setStatus(blog.getStatus());

        return blogResponse;
    }

    public static Blog fromRequestToEntity(BlogRequest request) {
        Blog blog = new Blog();
        blog.setPicture(request.getPicture());
        blog.setContent(request.getContent());
        blog.setTitleName(request.getTitleName());

        return blog;
    }
}
