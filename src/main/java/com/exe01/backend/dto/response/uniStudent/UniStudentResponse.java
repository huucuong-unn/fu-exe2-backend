package com.exe01.backend.dto.response.uniStudent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UniStudentResponse {
    private UUID id;
    private UUID subscriptionId;
    private UUID userId;
    private Integer remainSubscription;
    private String fullName;
    private String university;
    private String major;
    private String cv;
    private String profilePicture;
}
