package com.exe01.backend.dto.response.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogResponse {
    private UUID id;

    private UUID businessId;

    private String picture;

    private String titleName;

    private String content;
}
