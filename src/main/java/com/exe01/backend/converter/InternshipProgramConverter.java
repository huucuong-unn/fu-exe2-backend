package com.exe01.backend.converter;

import com.exe01.backend.dto.request.InternshipProgramRequest;
import com.exe01.backend.dto.response.internshipProgram.InternshipProgramResponse;
import com.exe01.backend.entity.InternshipProgram;

public class InternshipProgramConverter {
    public static InternshipProgramResponse fromEntityToInternshipProgramResponse(InternshipProgram internshipProgram) {
        InternshipProgramResponse internshipProgramResponse = new InternshipProgramResponse();
        internshipProgramResponse.setId(internshipProgram.getId());
        internshipProgramResponse.setBusinessId(internshipProgram.getBusiness().getId());
        internshipProgramResponse.setTitleName(internshipProgram.getTitleName());
        internshipProgramResponse.setDescription(internshipProgram.getDescription());
        internshipProgramResponse.setPicture(internshipProgram.getPicture());
        internshipProgramResponse.setBenefits(internshipProgram.getBenefits());
        internshipProgramResponse.setRequirements(internshipProgram.getRequirements());
        internshipProgramResponse.setSkillsAndKeywordRelate(internshipProgram.getSkillsAndKeywordRelate());
        internshipProgramResponse.setDuration(internshipProgram.getDuration());
        internshipProgramResponse.setLocation(internshipProgram.getLocation());
        internshipProgramResponse.setApplicationDeadline(internshipProgram.getApplicationDeadline());
        internshipProgramResponse.setStatus(internshipProgram.getStatus());

        return internshipProgramResponse;
    }

    public static InternshipProgram fromRequestToEntity(InternshipProgramRequest request) {
        InternshipProgram internshipProgram = new InternshipProgram();
        internshipProgram.setId(request.getBusinessId());
        internshipProgram.setPicture(request.getPicture());
        internshipProgram.setTitleName(request.getTitleName());
        internshipProgram.setDescription(request.getDescription());
        internshipProgram.setBenefits(request.getBenefits());
        internshipProgram.setRequirements(request.getRequirements());
        internshipProgram.setSkillsAndKeywordRelate(request.getSkillsAndKeywordRelate());
        internshipProgram.setLocation(request.getLocation());
        internshipProgram.setDuration(request.getDuration());
        internshipProgram.setApplicationDeadline(request.getApplicationDeadline());

        return internshipProgram;
    }
}
