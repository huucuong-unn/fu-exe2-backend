package com.exe01.backend.dto.request.coze;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class CozeReviewCVRequest {
    MultipartFile file;
    UUID userId;
}
