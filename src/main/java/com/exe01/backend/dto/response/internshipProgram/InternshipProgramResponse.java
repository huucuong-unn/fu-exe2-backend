package com.exe01.backend.dto.response.internshipProgram;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InternshipProgramResponse {
    private UUID businessId;
    private UUID id;
    private String titleName;
    private String description;
    private String picture;
    private String benefits;
    private String requirements;
    private String skillsAndKeywordRelate;
    private String location;
    private Integer duration;
    private Date applicationDeadline;
    private String status;
}
