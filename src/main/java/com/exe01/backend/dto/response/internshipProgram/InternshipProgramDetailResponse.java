package com.exe01.backend.dto.response.internshipProgram;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InternshipProgramDetailResponse {
    private UUID id;
    private UUID businessId;
    private String picture;
    private String location;
    private String description;
    private String benefit;
    private String requirement;
    private String skillAndKeywordRelated;
    private String businessName;
    private String businessLocation;
    private String aboutBusiness;

}
