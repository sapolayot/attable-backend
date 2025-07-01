package com.project.attable.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.project.attable.entity.response.ConfigResponse;

@Repository
public class ConfigDaoImpl implements ConfigDao {
	
	@Value("${gapDay.eventReview}")
	private int eventReview;

	@Value("${gapDay.eventRelease}")
	private int eventRelease;

	@Value("${gapDay.eventPrepare}")
	private int eventPrepare;

	@Value("${gapDay.emailMinimumSeat}")
	private int emailMinimumSeat;

	@Value("${gapDay.emailConfirmSeat}")
	private int emailConfirmSeat;
	
	@Override
	public ConfigResponse getConfig() {
		ConfigResponse config = new ConfigResponse();
		config.setEventReview(eventReview);
		config.setEventRelease(eventRelease);
		config.setEventPrepare(eventPrepare);
		config.setEmailMinimumSeat(emailMinimumSeat);
		config.setEmailConfirmSeat(emailConfirmSeat);
		return config;
	}

}
