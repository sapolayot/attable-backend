package com.project.attable.security.requestmodel;

import lombok.Data;

@Data
public class OmiseRequest {
	private String token;
	private Long amount;
	private Long reserveId;
}
