package com.project.attable.entity.response;

import com.project.attable.entity.PayChef;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayChefResponse {
	private boolean success;
	private PayChef payChef;
	private String error;
}
