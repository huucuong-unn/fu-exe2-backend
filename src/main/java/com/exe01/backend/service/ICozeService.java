package com.exe01.backend.service;

import com.exe01.backend.dto.request.coze.CozeCreateChatRequest;
import com.exe01.backend.dto.response.coze.CozeCreateChatResponse;
import com.exe01.backend.dto.response.coze.CozeFeedbackResponse;
import com.exe01.backend.dto.response.coze.CozeUploadFileResponse;
import com.exe01.backend.exception.BaseException;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ICozeService {
    CozeCreateChatResponse createChat(String fileId) throws BaseException;

    CozeFeedbackResponse uploadFile(MultipartFile file, UUID userId) throws BaseException;

}
