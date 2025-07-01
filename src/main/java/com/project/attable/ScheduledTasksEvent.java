package com.project.attable;

import java.util.List;

import com.project.attable.entity.Event;
import com.project.attable.entity.EventStatus;
import com.project.attable.entity.SubEvent;
import com.project.attable.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledTasksEvent {
	@Autowired
	private EventRepository eventRepository;

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

	// Change main event when all subevent pending for confirm
//	@Scheduled(fixedRateString = "${event.interval}")
//	public void intervalChangeInprogressToPendingForConfirm() {
//		List<Event> listEvent = eventRepository.findByStatusAndCurrentDate(EventStatus.In_progress);
//		boolean allPending = true;
//		for (Event e : listEvent) {
//			for (SubEvent s : e.getSubevent()) {
//				if (!s.getStatus().equals(EventStatus.Pending_for_confirm)) {
//					allPending = false;
//					break;
//				}
//			}
//			if (allPending) {
//				e.setStatus(EventStatus.Pending_for_confirm);
//				eventRepository.save(e);
//			}
//		}
//	}

	// Change main event when all subevent completed
	@Scheduled(fixedRateString = "${event.interval}")
	public void intervalChangePendingForConfirmToCompleted() {
		List<Event> listEvent = eventRepository.findByStatusAndCurrentDate(EventStatus.In_progress);
		boolean allCompleted = true;
		for (Event e : listEvent) {
			for (SubEvent s : e.getSubevent()) {
				if (!s.getStatus().equals(EventStatus.Completed)) {
					allCompleted = false;
					break;
				}
			}
			if (allCompleted) {
				e.setStatus(EventStatus.Completed);
				eventRepository.save(e);
			}
		}
	}

}
