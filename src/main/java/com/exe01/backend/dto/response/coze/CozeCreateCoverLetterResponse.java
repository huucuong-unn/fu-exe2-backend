package com.exe01.backend.dto.response.coze;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CozeCreateCoverLetterResponse {
    private List<String> data;
    private UUID userId;
}
