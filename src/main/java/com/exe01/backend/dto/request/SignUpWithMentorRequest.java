package com.exe01.backend.dto.request;

import com.exe01.backend.dto.request.account.CreateAccountRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpWithMentorRequest {
    private CreateAccountRequest createAccountRequest;
}
