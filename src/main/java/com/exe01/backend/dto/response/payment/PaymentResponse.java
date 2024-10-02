package com.exe01.backend.dto.response.payment;

import com.exe01.backend.dto.response.user.UserResponse;
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
public class PaymentResponse {

    private UUID id;
    private UserResponse user;
    private String email;
    private Long refId;
    private int total;
    private String tierName;
    private String method;
    private Date createdDate;
    private String status;

}
