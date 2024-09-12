package com.exe01.backend.dto.request.user;

import com.exe01.backend.dto.request.BusinessRequest;
import com.exe01.backend.dto.request.UniStudentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String name;

    private String email;

    private Date dob;

    private String password;

    private String role;

    private UUID subscriptionId = UUID.fromString("6bc02c45-70f2-11ef-b077-a2f45e5369c6");

    private UniStudentRequest uniStudentRequest;

    private BusinessRequest businessRequest;

}
