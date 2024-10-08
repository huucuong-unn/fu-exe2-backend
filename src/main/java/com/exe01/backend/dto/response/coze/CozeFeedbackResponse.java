package com.exe01.backend.dto.response.coze;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CozeFeedbackResponse {
    private UUID userId;
    private List<Correction> spelling;
    private List<Sentence> sentences;
    private List<String> positions;
    @Data
    public static class Correction {
        private String incorrect;
        private String correct;
    }

    @Data
    public static class Sentence {
        private String original;
        private String revised;
    }

}



