package com.exe01.backend.controller;//package com.exe01.backend.controller;
//
//import com.exe01.backend.constant.ConstAPI;
//import com.exe01.backend.dto.response.coze.CozeUploadFileResponse;
//import com.exe01.backend.service.ICozeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/notifications")
//public class CozeController {
//
//    @Autowired
//    ICozeService cozeService;
//
//    @PostMapping(value = ConstAPI.CozeAPI.UPLOAD_FILE, consumes = "multipart/form-data")
//    public CozeUploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
//        // Here you can call the service to upload the file to the external API (e.g., Coze API)
//        // Assuming you have a service named `cozeService` and a method `uploadFile`
//    return cozeService.uploadFile(file);
//
//        // Handle response from the service
//    }
//
//
//
//
//}
