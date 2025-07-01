package com.project.attable.security.responsemodel;

import com.project.attable.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private User user;
    
    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public JwtAuthenticationResponse(String accessToken,User user) {
        this.accessToken = accessToken;
        this.user = user;
    }

}
