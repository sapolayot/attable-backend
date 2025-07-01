package com.project.attable.entity.response;

import java.util.List;

import com.project.attable.entity.Payment;
import com.project.attable.entity.Reservation;

import lombok.Data;

@Data
public class PaymentsResponse {
	private int totalPage;
	private Long totalElement;
	private List<Payment> payment;
	
}
