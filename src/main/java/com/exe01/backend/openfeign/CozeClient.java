package com.exe01.backend.openfeign;

import com.exe01.backend.dto.request.coze.CozeCreateChatRequest;
import com.exe01.backend.dto.response.coze.CozeCreateChatResponse;
import com.exe01.backend.dto.response.coze.CozeUploadFileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "https://api.coze.com/api")
public interface CozeClient {
    @PostMapping("/v3/chat")
    CozeCreateChatResponse createChat(CozeCreateChatRequest request);

    @PostMapping(value = "/v1/files/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CozeUploadFileResponse uploadFile(@RequestPart("file") MultipartFile file);
}
