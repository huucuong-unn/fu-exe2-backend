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
public class Top3Response {
    private UUID id;

    private String titleName;

    private String description;

    private String picture;

    private Date createDate;

    private String businessPicture;

    private String businessName;

    private String location;

    private String skill;
}
