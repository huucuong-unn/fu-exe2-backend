package com.exe01.backend.dto.response.coze;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CozeCreateInterviewQuestionResponse {
    private UUID userId;
    private InterviewQuestion interviewQuestion;

    @Data
    @AllArgsConstructor
    public static class InterviewQuestion {
        private String question;
        private String answer;
    }
}
