package com.project.attable.security.responsemodel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForgotPasswordResponse {
    private Boolean success;
    private String email;
    private String firstName;
    private String token;
}
