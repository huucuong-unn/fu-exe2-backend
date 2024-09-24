package com.exe01.backend.dto.request.coze;

import lombok.Data;

@Data
public class CozeCreateCoverLetterRequest {
    private String name;
    private String email;
    private String phone;
    private String company;
    private String language;
    private String experience;
    private String position;
}
