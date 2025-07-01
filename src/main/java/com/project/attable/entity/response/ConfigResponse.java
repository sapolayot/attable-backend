package com.project.attable.entity.response;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
public class ConfigResponse {

	private int eventReview;
	private int eventRelease;
	private int eventPrepare;
	private int emailMinimumSeat;
	private int emailConfirmSeat;	
	
}
