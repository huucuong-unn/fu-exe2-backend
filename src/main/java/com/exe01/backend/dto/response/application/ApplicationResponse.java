package com.exe01.backend.dto.response.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {
    private UUID id;
    private UUID internshipProgramId;
    private UUID uniStudentId;
    private String cv;
    private String status;
}
