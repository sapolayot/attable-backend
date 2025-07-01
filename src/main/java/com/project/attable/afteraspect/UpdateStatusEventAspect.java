package com.project.attable.afteraspect;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.project.attable.emailwithform.dto.MailRequest;
import com.project.attable.emailwithform.service.EmailWithFormService;
import com.project.attable.entity.Event;
import com.project.attable.entity.response.EventResponse;

@Aspect
@Configuration
public class UpdateStatusEventAspect {

	@Autowired
	EmailWithFormService emailwithformService;

	@Value("${admin.email.senderEmail}")
	String senderEmail;

	@Value("${emailform.template.update_event}")
	String template;

	@Value("${emailform.subject.update_event}")
	String subject;

	@Value("${linkEmail}")
	String linkEmail;

	@AfterReturning(value = "execution (* com.project.attable.dao.EventDaoImpl.editEventStatus(..))", returning = "returnObject")
	private void updateStatusEvent(JoinPoint joinPoint, Object returnObject) {
		EventResponse response = (EventResponse) returnObject;
		Event event = response.getEvent();
		if (response.isSuccess()
				&& (event.getStatus().toString().equals("Approved") || event.getStatus().toString().equals("Reject"))) {
			MailRequest request = new MailRequest();
			request.setFrom(senderEmail);
			request.setTemplate(template);
			request.setTo(event.getChef().getUser().getEmail());
			int category = 0;
			int eventNumber = event.getChef().getEvent().size();
			if (event.getEventCategory().toString().equals("Go_Prac")) {
				category = 0;
			} else {
				category = 1;
			}
			request.setSubject(subject + " (" + event.getStatus() + ")" + " - " + event.getEventName());
			Map<String, Object> model = new HashMap<>();
			model.put("category", category);
			model.put("eventstatus", event.getStatus().toString().toLowerCase());
			model.put("firstName", event.getChef().getUser().getFirstName());
			model.put("eventName", event.getEventName());
			model.put("id", String.format("%05d", eventNumber));
			model.put("link", linkEmail);
			model.put("subject", subject);
			emailwithformService.sendEmail(request, model);
		}
	}
}
