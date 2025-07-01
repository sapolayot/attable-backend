package com.project.attable.dao;

import java.util.List;

import com.project.attable.entity.Event;
import com.project.attable.entity.PayChef;
import com.project.attable.entity.SubEvent;
import com.project.attable.entity.response.EventResponse;
import com.project.attable.entity.response.EventsResponse;
import com.project.attable.entity.response.PayChefResponse;
import com.project.attable.entity.response.SubEventResponse;
import com.project.attable.entity.response.SubEventsResponse;

public interface EventDao {
	
	/**Queries*/
	EventsResponse getListAllEvents(int page,int pagesize);
	EventsResponse getListRequestingEvents(int page, int pagesize);
	EventsResponse getListUpcommingEvents(int page,int pagesize);
	EventsResponse getListInprogressEvents(int page,int pagesize);
	EventsResponse getListCompleteEvents(int page,int pagesize);
	EventsResponse getListCancelEvents(int page,int pagesize);
	EventsResponse getAllDraftEvents(int page,int pagesize);
	SubEventsResponse getListAllSubEvents(int page, int pagesize);
	SubEventsResponse getListRequestingSubEvents(int page, int pagesize);
	SubEventsResponse getListUpcommingSubEvents(int page, int pagesize);
	SubEventsResponse getListInprogressSubEvents(int page, int pagesize);
	SubEventsResponse getListCompleteSubEvents(int page, int pagesize);
	SubEventsResponse getListCancelSubEvents(int page,int pagesize);
	SubEventsResponse getAllDraftSubEvents(int page,int pagesize);
	Event getEventById(Long id);
	SubEvent getSubEventById(Long id);
	
	/**Mutation*/
	//Chef
	EventResponse createEvent(Event event);
	EventResponse editEvent(Event event);
	SubEventResponse editSubEvent(SubEvent subEvent);
	EventResponse cancelEvent(Long id);
	SubEventResponse cancelSubEvent(Long id, String reasonCancel);
	
	//Admin
	EventResponse editEventStatus(Event event);
	PayChefResponse updateCompletedEventStatus(PayChef payChef);
	SubEventResponse updateCancelSubEventStatus(SubEvent subEvent);
	
}
