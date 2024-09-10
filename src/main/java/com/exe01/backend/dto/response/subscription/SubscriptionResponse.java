package com.exe01.backend.dto.response.subscription;

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
public class SubscriptionResponse {
    private UUID id;
    private String planType;
    private Date startDate;
    private Date endDate;
}
