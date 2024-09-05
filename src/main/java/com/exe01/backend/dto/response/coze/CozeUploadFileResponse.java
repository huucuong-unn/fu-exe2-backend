package com.exe01.backend.dto.response.coze;

import lombok.Data;

@Data
public class CozeUploadFileResponse {
    private FileDataResponse data;
    @Data
    public class FileDataResponse {
        private long bytes;
        private long createdAt;
        private String fileName;
        private String id;
    }
}
