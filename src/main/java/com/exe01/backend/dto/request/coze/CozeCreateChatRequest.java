package com.exe01.backend.dto.request.coze;

import lombok.Data;

import java.util.List;

@Data
public class CozeCreateChatRequest {
    private String botId;
    private String userId ="demo";
    private boolean stream = false;
    private boolean autoSaveHistory=true;
    private List<AdditionalMessage> additionalMessages;

    @Data
    public static class AdditionalMessage {
        private String role = "user";
        private String contentType ="object_string";
        private String content;
    }
}
