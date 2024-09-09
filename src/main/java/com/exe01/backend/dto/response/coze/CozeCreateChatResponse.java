package com.exe01.backend.dto.response.coze;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CozeCreateChatResponse {
    private ChatData data;

    @Data
   public class ChatData {
        @JsonProperty("conversation_id")
        private String conversationId;
        private String bot_id;
    }

}
