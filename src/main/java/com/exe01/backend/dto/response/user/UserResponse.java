package com.exe01.backend.dto.response.user;

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
public class UserResponse {
    private UUID id;

    private String name;

    private String email;

    private Date dob;

    private String password;

    private String role;

    private String status;
}
