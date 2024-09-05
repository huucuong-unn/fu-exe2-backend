package com.exe01.backend.dto.response.coze;

import lombok.Data;

@Data
public class CozeCreateChatResponse {
    private ChatData data;

    @Data
    class ChatData {
        private String conversationId;
        private String bot_id;
    }

}
