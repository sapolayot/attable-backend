package com.project.attable.graphql.Mutation;

import com.project.attable.entity.EventStatus;
import com.project.attable.entity.PayChef;
import com.project.attable.entity.SubEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.project.attable.dao.EventDao;
import com.project.attable.dao.UserDao;
import com.project.attable.entity.Event;
import com.project.attable.entity.response.EventResponse;
import com.project.attable.entity.response.EventsResponse;
import com.project.attable.entity.response.PayChefResponse;
import com.project.attable.entity.response.SubEventEmailResponse;
import com.project.attable.entity.response.SubEventResponse;
import com.project.attable.entity.response.UserResponse;
import com.project.attable.repository.EventRepository;

@Component
public class EventMutation implements GraphQLMutationResolver{
	
	@Autowired
	private EventDao eventDao;
	
	@PreAuthorize("hasRole('ROLE_CHEF')")
	public EventResponse createEvent(Event event) {
		return eventDao.createEvent(event);
	}
	
	@PreAuthorize("hasRole('ROLE_CHEF')")
	public EventResponse editEvent(Event event) {
		return eventDao.editEvent(event);
	}
	
	@PreAuthorize("hasRole('ROLE_CHEF')")
	public SubEventResponse editSubEvent(SubEvent subEvent) {
		return eventDao.editSubEvent(subEvent);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public EventResponse editEventStatus(Event event) {
		return eventDao.editEventStatus(event);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public PayChefResponse updateCompletedEventStatus(PayChef payChef) {
		return eventDao.updateCompletedEventStatus(payChef);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public SubEventResponse updateCancelSubEventStatus(SubEvent subEvent) {
		return eventDao.updateCancelSubEventStatus(subEvent);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_CHEF')")
	public EventResponse cancelEvent(Long id){
		return eventDao.cancelEvent(id);
	}
	@PreAuthorize("hasAnyRole('ROLE_CHEF')")
	public SubEventResponse cancelSubEvent(Long id, String reasonCancel) {
		return eventDao.cancelSubEvent(id, reasonCancel);
	}
	

	

	
}
