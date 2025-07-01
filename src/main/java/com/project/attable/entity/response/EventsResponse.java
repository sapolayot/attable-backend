package com.project.attable.entity.response;

import java.util.List;

import com.project.attable.entity.Event;

import lombok.Data;

@Data
public class EventsResponse {
	private int totalPage;
	private Long totalElement;
	private List<Event> events;
}
