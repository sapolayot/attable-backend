package com.project.attable.emailwithform.dto;

import lombok.Data;

@Data
public class MailRequest {
	private String to;
	private String from;
	private String subject;
	private String template;
}
