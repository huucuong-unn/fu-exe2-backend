package com.exe01.backend.dto.response.coze;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CozeMessageListResponse {

    @JsonProperty("data")
    List<ChatData> data;

    @Data
    public static  class ChatData {
        @JsonProperty("bot_id")
        private String botId;

        @JsonProperty("chat_id")
        private String chatId;

        private String content;

        @JsonProperty("content_type")
        private String contentType;

        @JsonProperty("conversation_id")
        private String conversationId;

        @JsonProperty("created_at")
        private long createdAt;
    }
}

