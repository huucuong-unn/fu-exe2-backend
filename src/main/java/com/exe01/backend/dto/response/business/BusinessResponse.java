package com.exe01.backend.dto.response.business;

import com.exe01.backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

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
