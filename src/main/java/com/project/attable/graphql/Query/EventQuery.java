package com.project.attable.graphql.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.project.attable.dao.EventDao;
import com.project.attable.entity.Event;
import com.project.attable.entity.SubEvent;
import com.project.attable.entity.response.EventsResponse;
import com.project.attable.entity.response.SubEventsResponse;

@Component
public class EventQuery implements GraphQLQueryResolver {
	@Autowired
	private EventDao eventDao;

	public EventsResponse getListAllEvents(int page, int pagesize) {
		return eventDao.getListAllEvents(page, pagesize);
	}
	
	public EventsResponse getListRequestingEvents(int page, int pagesize) {
		return eventDao.getListRequestingEvents(page, pagesize);
	}
	
	public EventsResponse getListUpcommingEvents(int page, int pagesize) {
		return eventDao.getListUpcommingEvents(page, pagesize);
	}

	public EventsResponse getListInprogressEvents(int page, int pagesize) {
		return eventDao.getListInprogressEvents(page, pagesize);
	}
	
	public EventsResponse getListCompleteEvents(int page, int pagesize) {
		return eventDao.getListCompleteEvents(page, pagesize);
	}
	
	public EventsResponse getListCancelEvents(int page, int pagesize) {
		return eventDao.getListCancelEvents(page, pagesize);
	
	}
	
	public SubEventsResponse getListAllSubEvents(int page, int pagesize) {
		return eventDao.getListAllSubEvents(page, pagesize);
	}
	
	public SubEventsResponse getListRequestingSubEvents(int page, int pagesize) {
		return eventDao.getListRequestingSubEvents(page, pagesize);
	}

	public SubEventsResponse getListUpcommingSubEvents(int page, int pagesize) {
		return eventDao.getListUpcommingSubEvents(page, pagesize);
	}
	
	public SubEventsResponse getListInprogressSubEvents(int page, int pagesize) {
		return eventDao.getListInprogressSubEvents(page, pagesize);
	}

	public SubEventsResponse getListCompleteSubEvents(int page, int pagesize) {
		return eventDao.getListCompleteSubEvents(page, pagesize);
	}
	
	public SubEventsResponse getListCancelSubEvents(int page, int pagesize) {
		return eventDao.getListCancelSubEvents(page, pagesize);
	}
	
	public Event getEventById(Long id) {
		return eventDao.getEventById(id);
	}

	public SubEvent getSubEventById(Long id) {
		return eventDao.getSubEventById(id);
	}

}
