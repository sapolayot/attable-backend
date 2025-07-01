package com.project.attable;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.project.attable.afteraspect.DateFormatter;
import com.project.attable.afteraspect.NotificationEvent;
import com.project.attable.emailwithform.service.EmailWithFormService;
import com.project.attable.entity.EventStatus;
import com.project.attable.entity.SubEvent;
import com.project.attable.repository.SubEventRepository;

@Component
public class ScheduledTasksNotification {

	@Autowired
	EmailWithFormService emailwithformService;

	@Value("${admin.email.senderEmail}")
	String senderEmail;

	@Value("${emailform.template.minimum_seat}")
	String template;

	@Value("${emailform.template.summary_before_seven}")
	String template2;

	@Value("${emailform.template.summary_before_five}")
	String template3;

	@Value("${emailform.template.summary_tomorrow}")
	String template4;

	@Value("${linkEmail}")
	String linkEmail;

	@Value("${gapDay.eventPrepare}")
	private long eventPrepare;

	@Value("${gapDay.emailMinimumSeat}")
	private long emailMinimumSeat;

	@Value("${gapDay.emailConfirmSeat}")
	private long emailConfirmSeat;

	@Autowired
	private SubEventRepository subeventRepository;

	private NotificationEvent notificationEvent;

	@PostConstruct
	public void initEmail() {
		notificationEvent = new NotificationEvent();
		notificationEvent.setDateFormatter(new DateFormatter());
		notificationEvent.setEmailwithformService(emailwithformService);
		notificationEvent.setSenderEmail(senderEmail);
		notificationEvent.setTemplate(template);
		notificationEvent.setTemplate2(template2);
		notificationEvent.setTemplate3(template3);
		notificationEvent.setTemplate4(template4);
		notificationEvent.setLinkEmail(linkEmail);
	}

	// @Scheduled(fixedRateString = "${subevent.interval}")
	@Scheduled(cron = "${notification.time}")
	public void intervalMinimumSeat() {
		List<SubEvent> subEvent = subeventRepository.findByStatus(EventStatus.Approved);
		LocalDateTime now = LocalDateTime.now();
		for (SubEvent subE : subEvent) {
			long dayEventStart = now.until(subE.getEventDate(), ChronoUnit.DAYS) + 1;
			if (dayEventStart == emailMinimumSeat) {
				notificationEvent.sendEmailForMinimumSeat(subE, emailMinimumSeat, emailConfirmSeat);
			}
		}
	}

//	@Scheduled(fixedRateString = "${subevent.interval}")
	@Scheduled(cron = "${notification.time}")
	public void intervalBefore7DaysEvent() {
		List<SubEvent> subEvent = subeventRepository.findByStatus(EventStatus.Approved);
		LocalDateTime now = LocalDateTime.now();
		for (SubEvent subE : subEvent) {
			long dayEventStart = now.until(subE.getEventDate(), ChronoUnit.DAYS) + 1;
			if (dayEventStart == eventPrepare) {
				// Send Email 7 days
				notificationEvent.sendEmailForBefore7DaysEvent(subE, eventPrepare, emailConfirmSeat);
			}
		}
	}

//	@Scheduled(fixedRateString = "${subevent.interval}")
	@Scheduled(cron = "${notification.time}")
	public void intervalBefore5DaysEvent() {
		List<SubEvent> subEvent = subeventRepository.findByStatus(EventStatus.Approved);
		LocalDateTime now = LocalDateTime.now();
		for (SubEvent subE : subEvent) {
			long dayEventStart = now.until(subE.getEventDate(), ChronoUnit.DAYS) + 1;
			if (dayEventStart == emailConfirmSeat) {
				// Send Email 5 days
				notificationEvent.sendEmailForBefore5DaysEvent(subE, emailConfirmSeat);
			}
		}
	}

//	@Scheduled(fixedRateString = "${subevent.interval}")
	@Scheduled(cron = "${notification.time}")
	public void intervalBeforeTomorrowEvent() {
		List<SubEvent> subEvent = subeventRepository.findByStatus(EventStatus.Approved);
		LocalDateTime now = LocalDateTime.now();
		for (SubEvent subE : subEvent) {
			long dayEventStart = now.until(subE.getEventDate(), ChronoUnit.DAYS) + 1;
			if (dayEventStart == 1) {
				// Send Email tomorrrow
				notificationEvent.sendEmailForBeforeTomorrowEvent(subE);
			}
		}
	}

}
