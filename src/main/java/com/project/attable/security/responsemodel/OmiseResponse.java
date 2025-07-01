package com.project.attable.security.responsemodel;

import com.project.attable.entity.Reservation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OmiseResponse {
	private boolean success;
	private Reservation reserve;
	private String errorCode;
	private String errorMessage;
}
