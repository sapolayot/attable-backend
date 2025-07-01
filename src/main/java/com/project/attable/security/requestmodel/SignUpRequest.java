package com.project.attable.security.requestmodel;

import javax.validation.constraints.*;

import com.project.attable.entity.RoleName;

import lombok.Data;

@Data
public class SignUpRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Size(min = 10, max = 10)
    private String phoneNumber;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;
	
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
    
    private RoleName roleName;


}
