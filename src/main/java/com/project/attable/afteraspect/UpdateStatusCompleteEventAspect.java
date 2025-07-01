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
import com.project.attable.entity.EventStatus;
import com.project.attable.entity.SubEvent;
import com.project.attable.entity.response.PayChefResponse;

@Aspect
@Configuration
public class UpdateStatusCompleteEventAspect {
	
	@Autowired
	EmailWithFormService emailwithformService;

	@Value("${admin.email.senderEmail}")
	String senderEmail;

	@Value("${emailform.template.completed_event}")
	String template;

	@Value("${linkEmail}")
	String linkEmail;
	
	private DateFormatter dateFormatter = new DateFormatter();
	
	@AfterReturning(value = "execution (* com.project.attable.dao.EventDaoImpl.updateCompletedEventStatus(..))", returning = "returnObject")
	public void updateCompletedEventStatus(JoinPoint joinPoint, Object returnObject) {
		PayChefResponse response = (PayChefResponse) returnObject;
		if (response.isSuccess()) {
			SubEvent subEvent = response.getPayChef().getSubEvent();
			EventStatus status = subEvent.getStatus();
			if(status.equals(EventStatus.Completed) ||status.equals(EventStatus.Hold_Payment)) {
				MailRequest request = new MailRequest();
				request.setFrom(senderEmail);
				request.setTemplate(template);
				request.setTo(subEvent.getEvent().getChef().getUser().getEmail());
				String id = String.format("%05d", subEvent.getEvent().getId());
				String groupId = id+"("+(subEvent.getSubIndex()+1)+")";
				String subject = "";
				if(status.equals(EventStatus.Completed)) {
					subject = "(Successful Payment) Event Number "+groupId +" - " + subEvent.getEvent().getEventName();
				} else if (status.equals(EventStatus.Hold_Payment)) {
					subject = "(Payment on Hold) Event Number "+groupId +" - " + subEvent.getEvent().getEventName();
				}	
				request.setSubject(subject);
				Map<String, Object> model = new HashMap<>();
				model.put("subject", subject);
				model.put("chefName", subEvent.getEvent().getChef().getUser().getFirstName());
				model.put("groupId", groupId);
				model.put("eventName", subEvent.getEvent().getEventName());
				model.put("eventDate", dateFormatter.convertDateForSendEmail(subEvent.getEventDate()));
				model.put("status", subEvent.getStatus());
				model.put("link", linkEmail);
				emailwithformService.sendEmail(request, model);
			}

		}
	}
}
