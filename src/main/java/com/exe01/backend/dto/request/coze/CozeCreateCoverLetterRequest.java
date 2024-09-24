package com.exe01.backend.dto.request.coze;

import lombok.Data;

import java.util.UUID;

@Data
public class CozeCreateCoverLetterRequest {
    private UUID userId;
    private String name;
    private String email;
    private String phone;
    private String company;
    private String language;
    private String experience;
    private String position;
}
