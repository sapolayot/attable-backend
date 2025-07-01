package com.project.attable.afteraspect;

import java.util.HashMap;
import java.util.Map;
import com.project.attable.emailwithform.dto.MailRequest;
import com.project.attable.emailwithform.service.EmailWithFormService;
import com.project.attable.entity.SubEvent;
import com.project.attable.entity.User;

import lombok.Data;

@Data
public class NotificationEvent {

	EmailWithFormService emailwithformService;

	String senderEmail;

	String template;

	String linkEmail;
	
	private DateFormatter dateFormatter;

	public void sendEmailForMinimumSeat(SubEvent subEvent,long emailMinimumSeat,long eventPrepare) {
		try {
			User user = subEvent.getEvent().getChef().getUser();
			
			MailRequest request = new MailRequest();
			request.setFrom(senderEmail);
			request.setTo(user.getEmail());
			String id = String.format("%05d", subEvent.getEvent().getId());
			String groupId = "(ID "+id+"-"+(subEvent.getSubIndex()+1)+")";
			String subject = "Minimum Seat Alert "+groupId+" - "+subEvent.getEvent().getEventName();
			request.setSubject(subject);
			request.setTemplate(template);
			Map<String, Object> model = new HashMap<>();
			model.put("subject", subject);
			model.put("chefName", user.getFirstName());
			model.put("id", id);
			model.put("eventName", subEvent.getEvent().getEventName());
			model.put("eventDate", dateFormatter.convertDateForSendEmail(subEvent.getEventDate()));
			model.put("booked", subEvent.getMaxSeat() - subEvent.getCurrentSeat());
			model.put("maxSeat", subEvent.getMaxSeat());
			model.put("leftSeat", subEvent.getMinSeat() - subEvent.getCurrentSeat());
			model.put("bookingDate", dateFormatter.convertDateForSendEmail(dateFormatter.spectifyDate(subEvent.getEventDate(),-7)));
			model.put("eventPrepare", eventPrepare);
			model.put("groupId",groupId);
			model.put("emailMinimumSeat", emailMinimumSeat);
			model.put("link", linkEmail);
			
			emailwithformService.sendEmail(request, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	String template2;

	public void sendEmailForBefore7DaysEvent(SubEvent subEvent, long eventPrepare, long emailConfirmSeat) {
		try {
			User user = subEvent.getEvent().getChef().getUser();
			
			MailRequest request = new MailRequest();
			request.setFrom(senderEmail);
			request.setTo(user.getEmail());
			String id = String.format("%05d", subEvent.getEvent().getId());
			String groupId = "(ID "+id+"-"+(subEvent.getSubIndex()+1)+")";
			String subject = "Event Summary "+groupId+" "+eventPrepare+"D- "+subEvent.getEvent().getEventName();
			request.setSubject(subject);
			request.setTemplate(template2);

			Map<String, Object> model = new HashMap<>();
			model.put("subject", subject);
			model.put("chefName", user.getFirstName());
			model.put("id", id);
			model.put("eventName", subEvent.getEvent().getEventName());
			model.put("eventDate", dateFormatter.convertDateForSendEmail(subEvent.getEventDate()));
			model.put("groupId",groupId);
			model.put("eventPrepare", eventPrepare);
			model.put("emailConfirmSeat", emailConfirmSeat);
			model.put("link", linkEmail);
			
			emailwithformService.sendEmail(request, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	String template3;

	public void sendEmailForBefore5DaysEvent(SubEvent subEvent, long emailConfirmSeat) {
		try {
			User user = subEvent.getEvent().getChef().getUser();
			
			MailRequest request = new MailRequest();
			request.setFrom(senderEmail);
			request.setTo(user.getEmail());
			String id = String.format("%05d", subEvent.getEvent().getId());
			String groupId = "(ID "+id+"-"+(subEvent.getSubIndex()+1)+")";
			String subject = "Event Summary "+groupId+" "+emailConfirmSeat+"D- "+subEvent.getEvent().getEventName();
			request.setSubject(subject);
			request.setTemplate(template3);

			Map<String, Object> model = new HashMap<>();
			model.put("subject", subject);
			model.put("chefName", user.getFirstName());
			model.put("id", id);
			model.put("groupId",groupId);
			model.put("eventDate", dateFormatter.convertDateForSendEmail(subEvent.getEventDate()));
			model.put("eventName", subEvent.getEvent().getEventName());
			model.put("emailConfirmSeat", emailConfirmSeat);
			model.put("link", linkEmail);
			model.put("linkClick", linkEmail+"/event/sub_event/diner/"+subEvent.getId());
			emailwithformService.sendEmail(request, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	String template4;

	public void sendEmailForBeforeTomorrowEvent(SubEvent subEvent) {
		try {
			User user = subEvent.getEvent().getChef().getUser();
			
			MailRequest request = new MailRequest();
			request.setFrom(senderEmail);
			request.setTo(user.getEmail());
			String id = String.format("%05d", subEvent.getEvent().getId());
			String groupId = "(ID "+id+"-"+(subEvent.getSubIndex()+1)+")";
			String subject = "Event Summary "+groupId+" "+"1D- "+subEvent.getEvent().getEventName();
			request.setSubject(subject);
			request.setTemplate(template4);

			Map<String, Object> model = new HashMap<>();
			model.put("subject", subject);
			model.put("chefName", user.getFirstName());
			model.put("eventName", subEvent.getEvent().getEventName());
			model.put("eventDate", dateFormatter.convertDateForSendEmail(subEvent.getEventDate()));
			model.put("id", id);
			model.put("groupId",groupId);
			model.put("link", linkEmail);
			
			emailwithformService.sendEmail(request, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
