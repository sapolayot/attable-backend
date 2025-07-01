package com.project.attable.afteraspect;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.project.attable.emailwithform.dto.MailRequest;
import com.project.attable.emailwithform.service.EmailWithFormService;
import com.project.attable.entity.AccountForRefund;
import com.project.attable.entity.RejectDinerCancel;
import com.project.attable.entity.Reservation;
import com.project.attable.entity.ReserveStatus;
import com.project.attable.entity.response.RefundResponse;
import com.project.attable.entity.response.ReservationResponse;
import com.project.attable.security.GenerateToken;
import com.project.attable.security.JwtTokenProvider;

@Aspect
@Configuration
public class CancelBookingAspect {
	@Autowired
	EmailWithFormService emailwithformService;

	@Value("${admin.email.senderEmail}")
	String senderEmail;

	@Value("${emailform.template.cancel_booking_diner}")
	String template;

	@Value("${linkEmail}")
	String linkEmail;

	private DateFormatter dateFormatter = new DateFormatter();

	@AfterReturning(value = "execution (* com.project.attable.dao.ReservationDaoImpl.updateCancelReservationStatus(..))", returning = "returnObject")
	private void sendEmailCancelBooking(JoinPoint joinPoint, Object returnObject) {
		ReservationResponse response = (ReservationResponse) returnObject;
		if (response.isSuccess()) {
			Reservation reserve = response.getReserve();
			MailRequest request = new MailRequest();
			ReserveStatus status = reserve.getStatus();
			if (status.equals(ReserveStatus.Pending) || status.equals(ReserveStatus.Confirmed_Seat)) {
				request.setFrom(senderEmail);
				request.setTemplate(template);
				request.setTo(reserve.getUser().getEmail());
				String subject = "Booking cancelled for " + reserve.getSubevent().getEvent().getEventName();
				request.setSubject(subject);
				String token = GenerateToken.getTokenForEmailByReserveId(reserve.getId(), reserve.getUser().getEmail());
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
				model.put("linkClick", linkEmail + "/refund/" + reserve.getId()+"?token="+token+"&email="+reserve.getUser().getEmail());
				model.put("link", linkEmail);
				model.put("reject", false);
				if (!reserve.getDetailCancelReject().isEmpty()
						&& reserve.getStatus().equals(ReserveStatus.Confirmed_Seat)) {
					RejectDinerCancel dinerCancel = reserve.getDetailCancelReject()
							.get(reserve.getDetailCancelReject().size() - 1);
					model.put("rejectTitle", dinerCancel.getRejectTitle());
					model.put("rejectText", dinerCancel.getRejectText());
					model.put("reject", true);
				}
				emailwithformService.sendEmail(request, model);
			}
		}
	}

	@Value("${emailform.template.ac_hoc}")
	String template1;

	@AfterReturning(value = "execution (* com.project.attable.dao.RefundDaoImpl.updateBankAccountForRefund(..))", returning = "returnObject")
	public void sendEmailCancelTellChef(JoinPoint joinPoint, Object returnObject) {
		RefundResponse response = (RefundResponse) returnObject;
		if (response.isSuccess()) {
			AccountForRefund acc = response.getAcc();
			Reservation reserve = acc.getReserve();
			if (reserve.getStatus().equals(ReserveStatus.Cancelled)) {
				MailRequest request = new MailRequest();
				request.setFrom(senderEmail);
				request.setTemplate(template1);
				request.setTo(reserve.getSubevent().getEvent().getChef().getUser().getEmail());
				String id = String.format("%05d", reserve.getSubevent().getEvent().getId());
				String groupId = id + "(" + (reserve.getSubevent().getSubIndex() + 1) + ")";
				String subject = "(Diner's Cancellation) Event Summary for Event Number " + groupId + " - "
						+ reserve.getSubevent().getEvent().getEventName();
				request.setSubject(subject);

				Map<String, Object> model = new HashMap<>();
				model.put("subject", subject);
				model.put("chefName", reserve.getSubevent().getEvent().getChef().getUser().getFirstName());
				model.put("groupId", groupId);
				model.put("eventName", reserve.getSubevent().getEvent().getEventName());
				model.put("eventDate", dateFormatter.convertDateForSendEmail(reserve.getSubevent().getEventDate()));
				model.put("link", linkEmail);
				emailwithformService.sendEmail(request, model);
			}
		}
	}

	@Value("${emailform.template.refund_completed}")
	String template2;

	@Value("${emailform.template.refund_incomplete}")
	String template3;

	@AfterReturning(value = "execution (* com.project.attable.dao.RefundDaoImpl.updateBankAccountForRefund(..))", returning = "returnObject")
	public void sendEmailRefund(JoinPoint joinPoint, Object returnObject) {
		RefundResponse response = (RefundResponse) returnObject;
		if (response.isSuccess()) {
			AccountForRefund acc = response.getAcc();
			Reservation reserve = acc.getReserve();
			if (reserve.getStatus().equals(ReserveStatus.Cancelled)) {
				MailRequest request = new MailRequest();
				request.setFrom(senderEmail);
				request.setTemplate(template2);
				request.setTo(reserve.getUser().getEmail());
				String subject = "Refund completed - Booking cancelled for "
						+ reserve.getSubevent().getEvent().getEventName();
				request.setSubject(subject);

				Map<String, Object> model = new HashMap<>();
				model.put("subject", subject);
				model.put("dinerName", reserve.getUser().getFirstName());
				model.put("reserveId", String.format("%05d", reserve.getId()));
				model.put("eventName", reserve.getSubevent().getEvent().getEventName());
				model.put("link", linkEmail);
				emailwithformService.sendEmail(request, model);
			} else if (reserve.getStatus().equals(ReserveStatus.Pending_Payment_Review)) {
				MailRequest request = new MailRequest();
				request.setFrom(senderEmail);
				request.setTemplate(template3);
				request.setTo(reserve.getUser().getEmail());
				String subject = "Refund incomplete - Booking cancelled for "
						+ reserve.getSubevent().getEvent().getEventName();
				request.setSubject(subject);

				String token = GenerateToken.getTokenForEmailByReserveId(reserve.getId(), reserve.getUser().getEmail());
				
				Map<String, Object> model = new HashMap<>();
				model.put("subject", subject);
				model.put("dinerName", reserve.getUser().getFirstName());
				model.put("reserveId", String.format("%05d", reserve.getId()));
				model.put("eventName", reserve.getSubevent().getEvent().getEventName());
				model.put("link", linkEmail);
				model.put("linkClick", linkEmail+"/refund/"+reserve.getId()+"?token="+token+"&email="+reserve.getUser().getEmail());
				emailwithformService.sendEmail(request, model);
			}
		}

	}
}
