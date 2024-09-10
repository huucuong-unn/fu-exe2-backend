package com.exe01.backend.openfeign;

import com.exe01.backend.dto.request.coze.CozeCreateChatRequest;
import com.exe01.backend.dto.response.coze.CozeCreateChatResponse;
import com.exe01.backend.dto.response.coze.CozeMessageListResponse;
import com.exe01.backend.dto.response.coze.CozeUploadFileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "cozeClient", url = "https://api.coze.com")
public interface CozeClient {
    @PostMapping("/v3/chat")
    CozeCreateChatResponse createChat(@RequestHeader("Authorization") String authorizationToken,@RequestBody CozeCreateChatRequest request);

    @PostMapping(value = "/v1/files/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CozeUploadFileResponse uploadFile(@RequestHeader("Authorization") String authorizationToken, @RequestPart("file") MultipartFile file);

    @GetMapping(value = "/v1/conversation/message/list")
    CozeMessageListResponse getMessageList(@RequestHeader("Authorization") String authorizationToken, @RequestParam("conversation_id") String conversationId);

}
