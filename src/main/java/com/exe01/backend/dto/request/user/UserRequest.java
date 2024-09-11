package com.exe01.backend.dto.request.user;

import com.exe01.backend.dto.request.BusinessRequest;
import com.exe01.backend.dto.request.UniStudentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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

    private UniStudentRequest uniStudentRequest;

    private BusinessRequest businessRequest;

}
