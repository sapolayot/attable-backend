package com.project.attable.entity.response;


import com.project.attable.entity.AccountForRefund;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefundResponse {
	private boolean success;
	private AccountForRefund acc;
	private String error;
}
