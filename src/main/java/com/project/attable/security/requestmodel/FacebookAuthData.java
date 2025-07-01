package com.project.attable.security.requestmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.project.attable.entity.RoleName;
import com.project.attable.utils.DateDeserializer;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FacebookAuthData implements Serializable {

    private String uid;

    private String firstName;

    private String lastName;

    private String email;

    private String token;

    private String profileImage;

    private RoleName roleName;

    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDateTime birthday;

}