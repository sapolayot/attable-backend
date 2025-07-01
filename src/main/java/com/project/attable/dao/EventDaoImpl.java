package com.project.attable.dao;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.project.attable.entity.Chef;
import com.project.attable.entity.Event;
import com.project.attable.entity.EventCategory;
import com.project.attable.entity.EventStatus;
import com.project.attable.entity.PayChef;
import com.project.attable.entity.Reservation;
import com.project.attable.entity.ReserveStatus;
import com.project.attable.entity.SubEvent;
import com.project.attable.entity.response.EventResponse;
import com.project.attable.entity.response.EventsResponse;
import com.project.attable.entity.response.PayChefResponse;
import com.project.attable.entity.response.SubEventResponse;
import com.project.attable.entity.response.SubEventsResponse;
import com.project.attable.repository.ChefRepository;
import com.project.attable.repository.EventRepository;
import com.project.attable.repository.PayChefRepository;
import com.project.attable.repository.ReservationRepository;
import com.project.attable.repository.SubEventRepository;

@Repository
public class EventDaoImpl implements EventDao {
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private SubEventRepository subEventRepository;

	@Autowired
	private ChefRepository chefRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private PayChefRepository paychefRepository;

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

	@Override
	public EventsResponse getListAllEvents(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<Event> events = eventRepository.getNotDraftAndDelete(pageRequest);
		EventsResponse response = new EventsResponse();
		response.setTotalElement(events.getTotalElements());
		response.setTotalPage(events.getTotalPages());
		response.setEvents(events.getContent());
		return response;
	}

	@Override
	public EventsResponse getListRequestingEvents(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<Event> events = eventRepository.findByStatus(pageRequest, EventStatus.Requesting);
		EventsResponse response = new EventsResponse();
		response.setTotalElement(events.getTotalElements());
		response.setTotalPage(events.getTotalPages());
		response.setEvents(events.getContent());
		return response;
	}

	@Override
	public EventsResponse getListUpcommingEvents(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "startDate"));
		Page<Event> events = eventRepository.findByStatus(pageRequest, EventStatus.Approved);
		EventsResponse response = new EventsResponse();
		response.setTotalElement(events.getTotalElements());
		response.setTotalPage(events.getTotalPages());
		response.setEvents(events.getContent());
		return response;
	}

	@Override
	public EventsResponse getListInprogressEvents(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<Event> events = eventRepository.findByStatus(pageRequest, EventStatus.In_progress);
		EventsResponse response = new EventsResponse();
		response.setTotalElement(events.getTotalElements());
		response.setTotalPage(events.getTotalPages());
		response.setEvents(events.getContent());
		return response;
	}

	@Override
	public EventsResponse getListCompleteEvents(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<Event> events = eventRepository.findByStatus(pageRequest, EventStatus.Completed);
		EventsResponse response = new EventsResponse();
		response.setTotalElement(events.getTotalElements());
		response.setTotalPage(events.getTotalPages());
		response.setEvents(events.getContent());
		return response;
	}

	@Override
	public EventsResponse getListCancelEvents(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<Event> events = eventRepository.findByStatus(pageRequest, EventStatus.Cancelled);
		EventsResponse response = new EventsResponse();
		response.setTotalElement(events.getTotalElements());
		response.setTotalPage(events.getTotalPages());
		response.setEvents(events.getContent());
		return response;
	}

	@Override
	public EventsResponse getAllDraftEvents(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<Event> events = eventRepository.findByStatus(pageRequest, EventStatus.Draft);
		EventsResponse response = new EventsResponse();
		response.setTotalElement(events.getTotalElements());
		response.setTotalPage(events.getTotalPages());
		response.setEvents(events.getContent());
		return response;
	}

	@Override
	public SubEventsResponse getListAllSubEvents(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<SubEvent> subevents = subEventRepository.getNotDraftAndDelete(pageRequest);
		SubEventsResponse response = new SubEventsResponse();
		response.setTotalElement(subevents.getTotalElements());
		response.setTotalPage(subevents.getTotalPages());
		response.setSubevents(subevents.getContent());
		return response;
	}

	@Override
	public SubEventsResponse getListRequestingSubEvents(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<SubEvent> subevents = subEventRepository.findByStatus(pageRequest, EventStatus.Requesting);
		SubEventsResponse response = new SubEventsResponse();
		response.setTotalElement(subevents.getTotalElements());
		response.setTotalPage(subevents.getTotalPages());
		response.setSubevents(subevents.getContent());
		return response;
	}

	@Override
	public SubEventsResponse getListUpcommingSubEvents(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "eventDate"));
		Page<SubEvent> subevents = subEventRepository.findByStatus(pageRequest, EventStatus.Approved);
		SubEventsResponse response = new SubEventsResponse();
		response.setTotalElement(subevents.getTotalElements());
		response.setTotalPage(subevents.getTotalPages());
		response.setSubevents(subevents.getContent());
		return response;
	}

	@Override
	public SubEventsResponse getListInprogressSubEvents(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<SubEvent> subevents = subEventRepository.findByStatus(pageRequest, EventStatus.In_progress);
		SubEventsResponse response = new SubEventsResponse();
		response.setTotalElement(subevents.getTotalElements());
		response.setTotalPage(subevents.getTotalPages());
		response.setSubevents(subevents.getContent());
		return response;
	}

	@Override
	public SubEventsResponse getListCompleteSubEvents(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<SubEvent> subevents = subEventRepository.findByStatus(pageRequest, EventStatus.Completed);
		SubEventsResponse response = new SubEventsResponse();
		response.setTotalElement(subevents.getTotalElements());
		response.setTotalPage(subevents.getTotalPages());
		response.setSubevents(subevents.getContent());
		return response;
	}

	@Override
	public SubEventsResponse getListCancelSubEvents(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<SubEvent> subevents = subEventRepository.findByStatus(pageRequest, EventStatus.Cancelled);
		SubEventsResponse response = new SubEventsResponse();
		response.setTotalElement(subevents.getTotalElements());
		response.setTotalPage(subevents.getTotalPages());
		response.setSubevents(subevents.getContent());
		return response;
	}

	@Override
	public SubEventsResponse getAllDraftSubEvents(int page, int pagesize) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
		Page<SubEvent> subevents = subEventRepository.findByStatus(pageRequest, EventStatus.Draft);
		SubEventsResponse response = new SubEventsResponse();
		response.setTotalElement(subevents.getTotalElements());
		response.setTotalPage(subevents.getTotalPages());
		response.setSubevents(subevents.getContent());
		return response;
	}

	@Override
	public Event getEventById(Long id) {
		Event event = eventRepository.findByIdIn(id);
		return event;
	}

	@Override
	public SubEvent getSubEventById(Long id) {
		SubEvent subevent = subEventRepository.findByIdIn(id);
		return subevent;
	}

	@Override
	public EventResponse createEvent(Event event) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startDate = event.getStartDate();
		LocalDateTime endDate = event.getEndDate();
		long dayStart = now.until(startDate, ChronoUnit.DAYS) + 1;
		long predictTime = eventReview + eventRelease + eventPrepare;
		if (dayStart < predictTime) {
			return new EventResponse(false, null, "Can not create at gap sum day");
		}
		if (startDate.isAfter(endDate)) {
			return new EventResponse(false, null, "Can not create start date after end date");
		}

		for (int i = 0; i < event.getSubevent().size(); i++) {
			SubEvent sub = event.getSubevent().get(i);
			sub.setSubIndex(i);
			event.getSubevent().set(i, sub);
		}
		Chef chef = chefRepository.findByIdIn(event.getChef().getId());
		List<Event> listEvent = chef.getEvent() != null ? chef.getEvent() : new ArrayList<>();
		listEvent.add(event);
		if (listEvent.size() == 1) {
			event.setEventCategory(EventCategory.Go_Prac);
		} else {
			event.setEventCategory(EventCategory.Go_Live);
		}
		event.setPriceOriginal(event.getPricePerSeat());
		event.setChef(chef);
		chef.setEvent(listEvent);
		event.setRequestingDate(LocalDateTime.now());
		eventRepository.save(event);
		return new EventResponse(true, event, null);
	}

	@Override
	public EventResponse editEvent(Event event) {
		Event oldEvent = eventRepository.findByIdIn(event.getId());
		if (oldEvent != null) {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime requestingDate = oldEvent.getRequestingDate();
			LocalDateTime startDate = event.getStartDate();
			LocalDateTime endDate = event.getEndDate();
			long dayRequesting = requestingDate.until(now, ChronoUnit.DAYS) + 1;
			long dayStart = now.until(startDate, ChronoUnit.DAYS) + 1;
			long predictTime = eventReview + eventRelease + eventPrepare;
			if (dayStart < predictTime) {
				return new EventResponse(false, null, "Can not create at gap sum day");
			}
			if (dayRequesting >= eventReview) {
				return new EventResponse(false, null, "Can not edit at gap day");
			}
			if (startDate.isAfter(endDate)) {
				return new EventResponse(false, null, "Can not create start date after end date");
			}
			if (oldEvent.getStatus().equals(EventStatus.Reject)) {
				oldEvent.setStatus(EventStatus.Clarify_for_review);
				for (SubEvent sub : oldEvent.getSubevent()) {
					sub.setStatus(EventStatus.Clarify_for_review);
				}
			}
			oldEvent.setStatus(event.getStatus());
			oldEvent.setEventName(event.getEventName());
			oldEvent.setEventDetail(event.getEventDetail());
			oldEvent.setEventType(event.getEventType());
			oldEvent.setPricePerSeat(event.getPricePerSeat());
			oldEvent.setPriceOriginal(event.getPricePerSeat());
			oldEvent.setLat(event.getLat());
			oldEvent.setLng(event.getLng());
			oldEvent.setCommissionType(event.getCommissionType());
			oldEvent.setHomeNo(event.getHomeNo());
			oldEvent.setStreet(event.getStreet());
			oldEvent.setSubDistrict(event.getSubDistrict());
			oldEvent.setDistrict(event.getDistrict());
			oldEvent.setCity(event.getCity());
			oldEvent.setPostalCode(event.getPostalCode());
			oldEvent.setStartDate(event.getStartDate());
			oldEvent.setEndDate(event.getEndDate());
			oldEvent.setRepeatOn(new ArrayList<>(event.getRepeatOn()));
			oldEvent.setRule(event.getRule());
			oldEvent.setOwnPlace(event.isOwnPlace());
			oldEvent.setAllergic(new ArrayList<>(event.getAllergic()));
			subEventRepository.deleteInBatch(oldEvent.getSubevent());
			oldEvent.setSubevent(new ArrayList<>(event.getSubevent()));
			for (int i = 0; i < event.getSubevent().size(); i++) {
				SubEvent sub = event.getSubevent().get(i);
				sub.setSubIndex(i);
				event.getSubevent().set(i, sub);
				sub.setEvent(oldEvent);
			}
			oldEvent.setMedia(new ArrayList<>(event.getMedia()));
			oldEvent.setMenu(new ArrayList<>(event.getMenu()));
			oldEvent.setAmenitiesPlace(new ArrayList<>(event.getAmenitiesPlace()));
			Event result = eventRepository.save(oldEvent);
			return new EventResponse(true, result, null);
		}
		return new EventResponse(false, null, "Event not found");
	}

	@Override
	public SubEventResponse editSubEvent(SubEvent subEvent) {
		SubEvent oldSub = subEventRepository.findByIdIn(subEvent.getId());
		if (oldSub != null) {
			if (!oldSub.getReserve().isEmpty()) {
				return new SubEventResponse(false, null, "Can not edit because diner have booked");
			}
			oldSub.setEventDate(subEvent.getEventDate());
			oldSub.setStartTime(subEvent.getStartTime());
			oldSub.setEndTime(subEvent.getEndTime());
			oldSub.setMaxSeat(subEvent.getMaxSeat());
			oldSub.setMinSeat(subEvent.getMinSeat());
			oldSub.setCurrentSeat(subEvent.getMaxSeat());
			return new SubEventResponse(true, subEventRepository.save(oldSub), null);
		}
		return new SubEventResponse(false, null, "SubEvent not found");
	}

	@Override
	public EventResponse cancelEvent(Long id) {
		Event oldEvent = eventRepository.findByIdIn(id);
		if (oldEvent != null) {
			oldEvent.setStatus(EventStatus.Delete);
			for (SubEvent sub : oldEvent.getSubevent()) {
				sub.setStatus(EventStatus.Delete);
			}
			return new EventResponse(true, eventRepository.save(oldEvent), null);
		}
		return new EventResponse(false, null, "Not have event");
	}

	@Override
	public SubEventResponse cancelSubEvent(Long id, String reasonCancel) {
		SubEvent oldSubEvent = subEventRepository.findByIdIn(id);
		if (oldSubEvent != null) {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime requestingDate = oldSubEvent.getEvent().getRequestingDate();
			long dayRequesting = requestingDate.until(now, ChronoUnit.DAYS) + 1;
			long endCancel = eventReview + eventRelease + eventPrepare;
			if (dayRequesting >= endCancel) {
				return new SubEventResponse(false, null, "Can not cancel at gap day");
			}
			oldSubEvent.setReasonCancel(reasonCancel);
			oldSubEvent.setStatus(EventStatus.Cancel_Requesting);
			return new SubEventResponse(true, subEventRepository.save(oldSubEvent), null);
		}
		return new SubEventResponse(false, null, null);
	}

	@Override
	public EventResponse editEventStatus(Event event) {
		Event oldEvent = eventRepository.findByIdIn(event.getId());
		if (oldEvent != null) {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime requestingDate = oldEvent.getRequestingDate();
			long days = requestingDate.until(now, ChronoUnit.DAYS) + 1;
			if (days >= eventReview) {
				return new EventResponse(false, null, "Can not edit at gap day");
			}
			oldEvent.setStatus(event.getStatus());
			for (SubEvent sub : oldEvent.getSubevent()) {
				sub.setStatus(event.getStatus());
			}
			if (event.getStatus().equals(EventStatus.Approved)) {
				oldEvent.setApproveDate(LocalDateTime.now());
				oldEvent.setEventType(event.getEventType());
				oldEvent.setCommission(event.getCommission());
				oldEvent.setCommissionType(event.getCommissionType());
				oldEvent.setPricePerSeat(event.getPricePerSeat());

			} else if (event.getStatus().equals(EventStatus.Pending_for_confirm)||
					event.getStatus().equals(EventStatus.Reject)) {
				if (oldEvent.getDetailReject() == null) {
					oldEvent.setDetailReject(new ArrayList<>());
				}
				if (event.getDetailReject() == null) {
					return new EventResponse(false, null, "Detail Reject not found");
				}
				event.getDetailReject().get(0).setRejectDate(LocalDateTime.now());
				oldEvent.getDetailReject().add(event.getDetailReject().get(0));
			}
			return new EventResponse(true, eventRepository.save(oldEvent), null);
		}
		return new EventResponse(false, null, "Event not found");
	}

	@Override
	public PayChefResponse updateCompletedEventStatus(PayChef payChef) {
		SubEvent oldSub = subEventRepository.findByIdIn(payChef.getSubEvent().getId());
		EventStatus status = payChef.getSubEvent().getStatus();
		if (oldSub != null) {
			if (status.equals(EventStatus.Completed) || status.equals(EventStatus.Hold_Payment)) {
				if (oldSub.getPayChef() != null) {
					payChef.setId(oldSub.getPayChef().getId());
				}
				if (status.equals(EventStatus.Hold_Payment) && !payChef.getText().equals("")) {
					return new PayChefResponse(false, null, "Can not text in hold_payment");
				}
				if (status.equals(EventStatus.Completed) && payChef.getText().equals("")) {
					return new PayChefResponse(false, null, "You must text");
				}
				oldSub.setStatus(status);
				payChef.setTransactionDate(LocalDateTime.now());
				payChef.setSubEvent(oldSub);
				oldSub.setPayChef(payChef);
				
				payChef=paychefRepository.save(payChef);
				payChef.getSubEvent().setStatus(status);
				payChef=paychefRepository.save(payChef);
				System.out.println("test >>> "+payChef.getSubEvent().getStatus());
				return new PayChefResponse(true,payChef , null);
				
			}
			return new PayChefResponse(false, null, "Please send completed or hold_payment");
		}
		return new PayChefResponse(false, null, "SubEvent not found");
	}

	@Override
	public SubEventResponse updateCancelSubEventStatus(SubEvent subEvent) {
		SubEvent oldSub = subEventRepository.findByIdIn(subEvent.getId());
		LocalDateTime nowDate = LocalDateTime.now();
		if (oldSub != null) {
			EventStatus status = subEvent.getStatus();
			if (status.equals(EventStatus.Approved) || status.equals(EventStatus.Pending)) {
				oldSub.setCancelDate(nowDate);
				oldSub.setStatus(status);
				return new SubEventResponse(true, subEventRepository.save(oldSub), null);
			}
			return new SubEventResponse(false, null, "Can not update status");
		}
		return new SubEventResponse(false, null, "Subevent not found");
	}
}
