package com.exe01.backend.dto.request.coze;

import lombok.Data;

import java.util.UUID;

@Data
public class CozeCreateInterviewQuestionRequest {
    private UUID userId;
    private String subject;
}
