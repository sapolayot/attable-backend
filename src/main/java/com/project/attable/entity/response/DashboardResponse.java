package com.project.attable.entity.response;

import lombok.Data;

@Data
public class DashboardResponse {
	private long chefSignUp;
	private long dinerSignUp;
	private long eventRegistered;
	private long eventCompleted;
	private long eventCancelled;
	private double chefCharge;
	private double attableFee;
	
}
