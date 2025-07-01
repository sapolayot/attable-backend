package com.project.attable;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.project.attable.afteraspect.ReviewBooking;
import com.project.attable.emailwithform.service.EmailWithFormService;
import com.project.attable.entity.EventStatus;
import com.project.attable.entity.Reservation;
import com.project.attable.entity.ReserveStatus;
import com.project.attable.entity.SubEvent;
import com.project.attable.repository.ReservationRepository;
import com.project.attable.repository.SubEventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasksSubEvent {
	@Autowired
	private SubEventRepository subeventRepository;

	@Autowired
	private ReservationRepository reservationRepository;

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

	/** Delay */
	// Clear Subevent approved to inprogress
	@Scheduled(fixedRateString = "${subevent.interval}")
	public void intervalCheckSubeventStatusApproved() throws InterruptedException {
		LocalTime now = LocalTime.now();
		List<SubEvent> subEvents = subeventRepository.findByStatusAndCurrentDate(EventStatus.Approved);
		for (SubEvent subE : subEvents) {
			LocalTime startTime = LocalTime.parse(subE.getStartTime());
			if (now.isAfter(startTime)) {
				subE.setStatus(EventStatus.In_progress);
				subE.getEvent().setStatus(EventStatus.In_progress);
				for (Reservation reserve : subE.getReserve()) {
					if (reserve.getStatus().equals(ReserveStatus.Confirmed_Seat)) {
						reserve.setStatus(ReserveStatus.In_progress);
					}
				}
				subeventRepository.save(subE);
			}
		}
	}

	@Autowired
	EmailWithFormService emailwithformService;

	@Value("${admin.email.senderEmail}")
	String senderEmail;

	@Value("${emailform.template.reviewbooking}")
	String template;

	@Value("${linkEmail}")
	String linkEmail;

	// Clear Subevent inprogress to pending for confirm
	@Scheduled(fixedRateString = "${subevent.interval}")
	public void intervalCheckSubeventStatusInProgress() {
		List<SubEvent> subEvents = subeventRepository.findByStatusAndCurrentDate(EventStatus.In_progress);
		for (SubEvent subE : subEvents) {
			LocalTime now = LocalTime.now();
			LocalTime endTime = LocalTime.parse(subE.getEndTime());
			if (now.isAfter(endTime)) {
				subE.setStatus(EventStatus.Pending_for_confirm);
				for (Reservation reserve : subE.getReserve()) {
					if (reserve.getStatus().equals(ReserveStatus.In_progress)) {
						reserve.setStatus(ReserveStatus.Completed);
					}
				}
				subeventRepository.save(subE);
			}
		}
	}

	@Scheduled(fixedRateString = "${subevent.interval}")
	public void sendReviewBooking() {
		List<SubEvent> subEvents = subeventRepository.findByStatusAndCurrentDate(EventStatus.Pending_for_confirm);
		for (SubEvent subE : subEvents) {
			LocalTime now = LocalTime.now();
			LocalTime endTime = LocalTime.parse(subE.getEndTime());
//			long minute = endTime.until(now, ChronoUnit.MINUTES);
			long seconds = endTime.until(now, ChronoUnit.SECONDS);
			if (seconds == 60) { // อย่าลืมปรับเป็น 2 ขั่วโมง
				ReviewBooking review = new ReviewBooking();
				review.setEmailwithformService(emailwithformService);
				review.setSenderEmail(senderEmail);
				review.setTemplate(template);
				review.setLinkEmail(linkEmail);
				review.sendEmailReviewEvent(subE);
			}
		}
	}

	// Clear subevent cancel seat 15 min and not upslip
	@Scheduled(fixedRateString = "${subevent.interval}")
	public void intervalCheckCancelSeat() throws InterruptedException {
		List<Reservation> listOldReservation = reservationRepository.findCountDown();
		for (Reservation oldReservation : listOldReservation) {
			LocalTime requestingTime = oldReservation.getRequestingDate().toLocalTime();
			LocalTime now = LocalTime.now();
			long minute = requestingTime.until(now, ChronoUnit.MINUTES);
			if (minute == 15 && oldReservation.getPayment() == null) {
				SubEvent subEvent = oldReservation.getSubevent();
				oldReservation.setStatus(ReserveStatus.Reject);
				if (subEvent.getCurrentSeat() <= subEvent.getMaxSeat()) {
					int currentSeat = subEvent.getCurrentSeat() + oldReservation.getJoin().size();
					subEvent.setCurrentSeat(currentSeat);
					oldReservation.setSubevent(subEvent);
				}
				reservationRepository.save(oldReservation);
			}
		}
	}

	// Clear subevent requesting to reject
	@Scheduled(fixedRateString = "${subevent.interval}")
	public void checkRejectEvent() {
		List<SubEvent> subEvent = subeventRepository.findByStatus(EventStatus.Requesting);
		LocalDateTime now = LocalDateTime.now();
		for (SubEvent event : subEvent) {
			LocalDateTime eventDate = event.getEventDate();
			long dayEventDate = now.until(eventDate, ChronoUnit.DAYS) + 1;
			long reviewDay = eventRelease + eventPrepare;
			if (dayEventDate <= reviewDay) {
				event.setStatus(EventStatus.Reject);
				event.getEvent().setStatus(EventStatus.Reject);
				subeventRepository.save(event);
			}
		}
	}

	// Clear subevent requesting to reject and upslip
	@Scheduled(cron = "${subevent.clearTime}")
	public void checkReturnSeat() {
		List<Reservation> listReserve = reservationRepository.findByStatus(ReserveStatus.Requesting);
		LocalDateTime now = LocalDateTime.now();
		for (Reservation reserve : listReserve) {
			LocalDateTime eventDate = reserve.getSubevent().getEventDate();
			long dayReject = now.until(eventDate, ChronoUnit.DAYS) + 1;
			if (dayReject <= emailConfirmSeat && reserve.getPayment() != null) {
				SubEvent subEvent = reserve.getSubevent();
				reserve.setStatus(ReserveStatus.Reject);
				if (subEvent.getCurrentSeat() <= subEvent.getMaxSeat()) {
					int nowSeat = subEvent.getCurrentSeat() + reserve.getJoin().size();
					subEvent.setCurrentSeat(nowSeat);
					reserve.setSubevent(subEvent);
				}
				reservationRepository.save(reserve);
			}
		}
	}

	// Clear auto refundable
	@Scheduled(cron = "${subevent.clearTime}")
	public void autoNonRefundable() {
		List<SubEvent> subEvents = subeventRepository.findByStatus(EventStatus.Approved);
		LocalDateTime now = LocalDateTime.now();
		for (SubEvent subE : subEvents) {
			long dayEventStart = now.until(subE.getEventDate(), ChronoUnit.DAYS) + 1;
			if (dayEventStart <= emailConfirmSeat) {
				for (Reservation reserve : subE.getReserve()) {
					reserve.setRefundable(false);
					reservationRepository.save(reserve);
				}
			}
		}
	}

}
