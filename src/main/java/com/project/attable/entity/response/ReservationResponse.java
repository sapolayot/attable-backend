package com.project.attable.entity.response;

import com.project.attable.entity.Reservation;
import com.project.attable.entity.SubEvent;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationResponse {
	private boolean success;
	private Reservation reserve;
	private String error;
}
