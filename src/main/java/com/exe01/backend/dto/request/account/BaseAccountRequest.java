package com.exe01.backend.dto.request.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseAccountRequest {
    @NotBlank(message = "This field must not be blank")
    @NotNull(message = "This field must not be null")
    @Size(max = 100, message = "Username must be less than or equal to 100 characters")
    private String username;

    @NotBlank(message = "This field must not be blank")
    @NotNull(message = "This field must not be null")
    @Size(min = 8, max = 80, message = "Password must be less than or equal to 100 characters")
    private String password;

    @NotBlank(message = "This field must not be blank")
    @NotNull(message = "This field must not be null")
    private MultipartFile avatarUrl;

    @NotBlank(message = "This field must not be blank")
    @NotNull(message = "This field must not be null")
    private String email;

    @NotBlank(message = "This field must not be blank")
    @NotNull(message = "This field must not be null")
    private String roleName;

    private String phoneNumber;
}
