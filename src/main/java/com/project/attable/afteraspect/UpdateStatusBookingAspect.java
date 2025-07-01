package com.project.attable.afteraspect;

import java.text.NumberFormat;
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

import com.project.attable.emailwithform.dto.MailRequest;
import com.project.attable.emailwithform.service.EmailWithFormService;
import com.project.attable.entity.Reservation;
import com.project.attable.entity.ReserveStatus;
import com.project.attable.entity.User;
import com.project.attable.entity.response.ReservationResponse;
import com.project.attable.security.responsemodel.OmiseResponse;

@Aspect
@Configuration
public class UpdateStatusBookingAspect {

	@Autowired
	EmailWithFormService emailwithformService;

	@Value("${admin.email.senderEmail}")
	String senderEmail;

	@Value("${emailform.template.booking}")
	String template;

	@Value("${linkEmail}")
	String linkEmail;

	@AfterReturning(value = "execution (* com.project.attable.dao.ReservationDaoImpl.editReservationStatus(..))", returning = "returnObject")
	private void sendEmailForUpdateBooking(JoinPoint joinPoint, Object returnObject) {
		ReservationResponse response = (ReservationResponse) returnObject;
		Reservation reserve = response.getReserve() != null ? response.getReserve() : new Reservation();
		if (response.isSuccess() && (reserve.getStatus().equals(ReserveStatus.Confirmed_Seat)
				|| reserve.getStatus().equals(ReserveStatus.Pending_Payment_Review))) {
			MailRequest request = new MailRequest();
			request.setFrom(senderEmail);
			request.setTemplate(template);
			request.setTo(reserve.getUser().getEmail());
			String subject = "";
			ReserveStatus status = reserve.getStatus();
			if (status.equals(ReserveStatus.Confirmed_Seat)) {
				subject = "Confirmed Seat for " + reserve.getSubevent().getEvent().getEventName();
			} else if (status.equals(ReserveStatus.Pending_Payment_Review)) {
				subject = "Bank Transfer incomplete for " + reserve.getSubevent().getEvent().getEventName();
			}
			request.setSubject(subject);
			Map<String, Object> model = new HashMap<>();
			int dinerSize = reserve.getJoin().size();
			double costSeat = dinerSize * reserve.getSubevent().getEvent().getPricePerSeat();

			User chef = reserve.getSubevent().getEvent().getChef().getUser();
			model.put("subject", subject);
			model.put("id", String.format("%05d", reserve.getId()));
			model.put("eventName", reserve.getSubevent().getEvent().getEventName());
			model.put("refundable", reserve.isRefundable());
			model.put("chefName", chef.getFirstName() + " " + chef.getLastName());
			model.put("firstName", reserve.getUser().getFirstName());
			model.put("status", status);
			String start[] = reserve.getSubevent().getStartTime().split(":");
			String end[] = reserve.getSubevent().getEndTime().split(":");
			LocalTime startTime = LocalTime.of(Integer.parseInt(start[0]), Integer.parseInt(start[1]));
			LocalTime endTime = LocalTime.of(Integer.parseInt(end[0]), Integer.parseInt(end[1]));
			DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("H:mma").withLocale(Locale.US);
			String time = formatterTime.format(startTime) + " - " + formatterTime.format(endTime);
			model.put("time", time);
			DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy,").withLocale(Locale.US);
			String eventDate = formatterDate.format(reserve.getSubevent().getEventDate());
			model.put("eventDate", eventDate);
			model.put("dinerSize", dinerSize);
			model.put("costSeat", NumberFormat.getNumberInstance(Locale.US).format(costSeat));
			model.put("link", linkEmail);
			model.put("linkurl", linkEmail + "/evidence/"+reserve.getId());
			emailwithformService.sendEmail(request, model);
		}
	}
	@AfterReturning(value = "execution (* com.project.attable.security.controller.OmiseController.charge(..))", returning = "returnObject")
	private void sendEmailForUpdateOmise(JoinPoint joinPoint, Object returnObject) {
		OmiseResponse response = (OmiseResponse) returnObject;
		Reservation reserve = response.getReserve() != null ? response.getReserve() : new Reservation();
		if(response.isSuccess() && reserve.getStatus().equals(ReserveStatus.Confirmed_Seat)) {
			MailRequest request = new MailRequest();
			request.setFrom(senderEmail);
			request.setTemplate(template);
			request.setTo(reserve.getUser().getEmail());
			String subject = "Confirmed Seat for " + reserve.getSubevent().getEvent().getEventName();
			request.setSubject(subject);
			Map<String, Object> model = new HashMap<>();
			int dinerSize = reserve.getJoin().size();
			double costSeat = dinerSize * reserve.getSubevent().getEvent().getPricePerSeat();
			ReserveStatus status = reserve.getStatus();
			User chef = reserve.getSubevent().getEvent().getChef().getUser();
			model.put("subject", subject);
			model.put("status", status);
			model.put("id", String.format("%05d", reserve.getId()));
			model.put("eventName", reserve.getSubevent().getEvent().getEventName());
			model.put("refundable", reserve.isRefundable());
			model.put("chefName", chef.getFirstName() + " " + chef.getLastName());
			model.put("firstName", reserve.getUser().getFirstName());
			String start[] = reserve.getSubevent().getStartTime().split(":");
			String end[] = reserve.getSubevent().getEndTime().split(":");
			LocalTime startTime = LocalTime.of(Integer.parseInt(start[0]), Integer.parseInt(start[1]));
			LocalTime endTime = LocalTime.of(Integer.parseInt(end[0]), Integer.parseInt(end[1]));
			DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("H:mma").withLocale(Locale.US);
			String time = formatterTime.format(startTime) + " - " + formatterTime.format(endTime);
			model.put("time", time);
			DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy,").withLocale(Locale.US);
			String eventDate = formatterDate.format(reserve.getSubevent().getEventDate());
			model.put("eventDate", eventDate);
			model.put("dinerSize", dinerSize);
			model.put("costSeat", NumberFormat.getNumberInstance(Locale.US).format(costSeat));
			model.put("link", linkEmail);
			emailwithformService.sendEmail(request, model);
		}
	}
}
