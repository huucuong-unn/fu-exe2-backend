package com.exe01.backend.dto.request.user;

import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String password;
    String role;
}
