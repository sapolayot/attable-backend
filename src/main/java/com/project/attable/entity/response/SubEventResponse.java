package com.project.attable.entity.response;

import com.project.attable.entity.SubEvent;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubEventResponse {
    private boolean success;
    private SubEvent subEvent;
    private String error;
}
