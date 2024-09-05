package com.exe01.backend.service;

import com.exe01.backend.dto.request.coze.CozeCreateChatRequest;
import com.exe01.backend.dto.response.coze.CozeCreateChatResponse;
import com.exe01.backend.dto.response.coze.CozeUploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ICozeService {
    CozeCreateChatResponse createChat(CozeCreateChatRequest request);
    CozeUploadFileResponse uploadFile(MultipartFile file);
}
