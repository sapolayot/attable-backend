package com.project.attable.afteraspect;

import java.util.HashMap;
import java.util.Map;
import com.project.attable.emailwithform.dto.MailRequest;
import com.project.attable.emailwithform.service.EmailWithFormService;
import com.project.attable.entity.Reservation;
import com.project.attable.entity.SubEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewBooking {

	private EmailWithFormService emailwithformService;

	private String senderEmail;

	private String template;

	private String linkEmail;

	public void sendEmailReviewEvent(SubEvent subevent) {
		for(Reservation reserve : subevent.getReserve()) {
			MailRequest request = new MailRequest();
			request.setFrom(senderEmail);
			request.setTemplate(template);
			request.setTo(reserve.getUser().getEmail());
			String subject =" Your booking for "+subevent.getEvent().getEventName()+" is completed.";
			request.setSubject(subject);
			Map<String, Object> model = new HashMap<>();
			model.put("subject", subject);
			model.put("eventName",subevent.getEvent().getEventName());
			model.put("chefName",subevent.getEvent().getChef().getUser().getFirstName());
			model.put("firstName", reserve.getUser().getFirstName());
			model.put("linkClick", linkEmail+"/review?reserveId="+reserve.getId()+"&chefId="+reserve.getSubevent().getEvent().getChef().getId());
			model.put("link", linkEmail);
			emailwithformService.sendEmail(request, model);
		}

	
	}

}
