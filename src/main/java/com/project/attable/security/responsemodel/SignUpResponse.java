package com.project.attable.security.responsemodel;

import java.util.Set;

import com.project.attable.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponse {
	private boolean success;
	private String message;
	private String jwt;
	private String email;
	private String firstName;
	private String lastName;
	private Set<Role> roles;
	private boolean isFacebookUser;

	public SignUpResponse(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

}
