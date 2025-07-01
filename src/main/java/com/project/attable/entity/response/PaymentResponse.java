package com.project.attable.entity.response;

import com.project.attable.entity.Payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {
	private boolean success;
	private Payment payment;
	private String error;
}
