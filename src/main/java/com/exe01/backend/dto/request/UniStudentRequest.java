package com.exe01.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UniStudentRequest {
    private UUID userId;
    private Integer remainSubscription;
    private String fullName;
    private String university;
    private String major;
    private MultipartFile cv;
    private MultipartFile profilePicture;
}
