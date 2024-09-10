package com.exe01.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InternshipProgramRequest {
    private UUID businessId;

    private String titleName;

    private String description;

    private String benefits;

    private String requirements;

    private String skillsAndKeywordRelate;

    private String location;

    private Integer duration;

    private String picture;

    private Date applicationDeadline;
}
