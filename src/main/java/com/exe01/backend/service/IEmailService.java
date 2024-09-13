package com.exe01.backend.service;


import com.exe01.backend.entity.EmailDetailsEntity;
import org.springframework.scheduling.annotation.Async;

public interface IEmailService {


    // Method
    // To send a simple email
    @Async
    String sendSimpleMail(EmailDetailsEntity details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetailsEntity details);
}
