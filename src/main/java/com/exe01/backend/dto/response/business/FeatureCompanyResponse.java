package com.exe01.backend.dto.response.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FeatureCompanyResponse {
    private String name;

    private UUID id;
}
