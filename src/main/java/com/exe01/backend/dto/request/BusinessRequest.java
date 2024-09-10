package com.exe01.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessRequest {
    private UUID userId;

    private String name;

    private String industry;

    private String location;

    private String description;

    private String logoPicture;

    private String backgroundPicture;

    private String status;

}
