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

    private  UUID id;
    private String name;

    private String email;

    private Date dob;

    private String password;

    private String role;

    private String status;


    private UUID subscriptionId = UUID.fromString("be6ca373-33fa-4e67-b779-b228e25a3e29");

    private UniStudentRequest uniStudentRequest;

    private BusinessRequest businessRequest;

    private String message;

}
