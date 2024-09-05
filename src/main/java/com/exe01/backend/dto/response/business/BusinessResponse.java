package com.exe01.backend.dto.response.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessResponse {

    private String name;

    private String industry;

    private String location;

    private String description;

    private String logoPicture;

    private String backgroundPicture;

    private String status;
}
