package com.project.attable.security.responsemodel;

import com.project.attable.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private Boolean success;
    private User user;
}
