package com.exe01.backend.service.impl;//package com.exe01.backend.service.impl;
//
//import com.exe01.backend.dto.request.coze.CozeCreateChatRequest;
//import com.exe01.backend.dto.response.coze.CozeCreateChatResponse;
//import com.exe01.backend.dto.response.coze.CozeUploadFileResponse;
//import com.exe01.backend.openfeign.CozeClient;
//import com.exe01.backend.service.ICozeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//@Service
//public class CozeService implements ICozeService {
//
//
//    @Autowired
//    private CozeClient cozeClient;
//
//    @Override
//    public CozeCreateChatResponse createChat(CozeCreateChatRequest request) {
//            return cozeClient.createChat(request);  // Call the Feign client
//    }
//
//    @Override
//    public CozeUploadFileResponse uploadFile(MultipartFile file) {
//            return cozeClient.uploadFile(file);
//    }
//}
