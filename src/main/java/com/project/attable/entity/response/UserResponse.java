package com.project.attable.entity.response;

import com.project.attable.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private boolean success;
    private User user;
}
