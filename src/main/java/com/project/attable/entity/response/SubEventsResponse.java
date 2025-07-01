package com.project.attable.entity.response;

import java.util.List;

import com.project.attable.entity.Event;
import com.project.attable.entity.SubEvent;

import lombok.Data;

@Data
public class SubEventsResponse {
	private int totalPage;
	private Long totalElement;
	private List<SubEvent> subevents;
}
