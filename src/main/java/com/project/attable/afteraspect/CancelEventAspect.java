package com.project.attable.afteraspect;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import com.project.attable.emailwithform.dto.MailRequest;
import com.project.attable.emailwithform.service.EmailWithFormService;
import com.project.attable.entity.EventStatus;
import com.project.attable.entity.Reservation;
import com.project.attable.entity.ReserveStatus;
import com.project.attable.entity.SubEvent;
import com.project.attable.entity.response.RefundResponse;
import com.project.attable.entity.response.SubEventResponse;
import com.project.attable.repository.ReservationRepository;
import com.project.attable.security.GenerateToken;

@Aspect
@Configuration
public class CancelEventAspect {

	@Autowired
	EmailWithFormService emailwithformService;

	@Value("${admin.email.senderEmail}")
	String senderEmail;

	@Value("${emailform.template.cancel_booking_chef}")
	String template;

	@Value("${linkEmail}")
	String linkEmail;

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private ReservationRepository reservationRepository;

	@AfterReturning(value = "execution (* com.project.attable.dao.EventDaoImpl.updateCancelSubEventStatus(..))", returning = "returnObject")
	public void updateCancelSubEventStatus(JoinPoint joinPoint, Object returnObject) {
		SubEventResponse response = (SubEventResponse) returnObject;
		if (response.isSuccess()) {
			SubEvent subEvent = response.getSubEvent();
			EventStatus status = subEvent.getStatus();
			if (status.equals(EventStatus.Pending)) {
				LocalDateTime nowDate = LocalDateTime.now();
				for (Reservation reserve : subEvent.getReserve()) {
					MailRequest request = new MailRequest();
					request.setFrom(senderEmail);
					request.setTemplate(template);
					request.setTo(reserve.getUser().getEmail());
					String subject = "Booking cancelled for " + reserve.getSubevent().getEvent().getEventName();
					request.setSubject(subject);

					String token = GenerateToken.getTokenForEmailByReserveId(reserve.getId(),
							reserve.getUser().getEmail());

					Map<String, Object> model = new HashMap<>();
					model.put("subject", subject);
					model.put("dinerName", reserve.getUser().getFirstName());
					model.put("id", String.format("%05d", reserve.getId()));
					model.put("eventName", reserve.getSubevent().getEvent().getEventName());
					model.put("status", reserve.getStatus());
					String start[] = reserve.getSubevent().getStartTime().split(":");
					String end[] = reserve.getSubevent().getEndTime().split(":");
					LocalTime startTime = LocalTime.of(Integer.parseInt(start[0]), Integer.parseInt(start[1]));
					LocalTime endTime = LocalTime.of(Integer.parseInt(end[0]), Integer.parseInt(end[1]));
					DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("H:mma").withLocale(Locale.US);
					String time = formatterTime.format(startTime) + " - " + formatterTime.format(endTime);
					model.put("time", time);
					DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy,")
							.withLocale(Locale.US);
					String eventDate = formatterDate.format(reserve.getSubevent().getEventDate());
					model.put("eventDate", eventDate);
					int dinerSize = reserve.getJoin().size();
					model.put("dinerSize", dinerSize);
					double price = reserve.getJoin().size() * reserve.getSubevent().getEvent().getPricePerSeat();
					model.put("price", price);
					model.put("refundable", reserve.isRefundable());
					model.put("linkClick", linkEmail + "/refund/" + reserve.getId() + "?token=" + token + "&email="
							+ reserve.getUser().getEmail());
					model.put("link", linkEmail);
					
					//-- update status reservation
					reserve.setCancelDate(nowDate);
					reserve.setStatus(ReserveStatus.Pending);
					reservationRepository.save(reserve);
//					if(!reserve.getDetailCancelReject().isEmpty() && reserve.getStatus().equals(ReserveStatus.Confirmed_Seat)) {
//						RejectDinerCancel dinerCancel = reserve.getDetailCancelReject().get(reserve.getDetailCancelReject().size()-1);
//						model.put("rejectTitle", dinerCancel.getRejectTitle());
//						model.put("rejectText", dinerCancel.getRejectText());
//						model.put("reject", true);
//					}	
					emailwithformService.sendEmail(request, model);
				}
			}
		}
	}

	@Value("${emailform.template.cancel_event}")
	String template1;

	@AfterReturning(value = "execution (* com.project.attable.dao.RefundDaoImpl.updateBankAccountForRefund(..))", returning = "returnObject")
	public void sendEmailCancelByChef(JoinPoint joinPoint, Object returnObject) {
		// Cancelled จริงๆแล้วบอก chef
		RefundResponse response = (RefundResponse) returnObject;
		if (response.isSuccess()) {
			SubEvent subEvent = response.getAcc().getReserve().getSubevent();
			if (subEvent.getStatus().equals(EventStatus.Cancelled)) {
				MailRequest request = new MailRequest();
				request.setFrom(senderEmail);
				request.setTemplate(template1);
				request.setTo(subEvent.getEvent().getChef().getUser().getEmail());
				String subject = "Approved - Event Cancellation - " + subEvent.getEvent().getEventName();
				request.setSubject(subject);

				String id = String.format("%05d", subEvent.getEvent().getId());
				String groupId = id + "(" + (subEvent.getSubIndex() + 1) + ")";

				Map<String, Object> model = new HashMap<>();
				model.put("subject", subject);
				model.put("chefName", subEvent.getEvent().getChef().getUser().getFirstName());
				model.put("groupId", groupId);
				model.put("eventName", subEvent.getEvent().getEventName());
				model.put("link", linkEmail);
				model.put("linkClick", linkEmail + "/event");
				emailwithformService.sendEmail(request, model);
			}
		}
	}
}
