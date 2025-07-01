package com.project.attable.entity.response;

import com.project.attable.entity.Event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventResponse {
    private boolean success;
    private Event event;
    private String error;
}
