package com.project.attable.entity.response;

import java.util.List;

import com.project.attable.entity.Event;
import com.project.attable.entity.Reservation;

import lombok.Data;

@Data
public class ReservationsResponse {
	private int totalPage;
	private Long totalElement;
	private List<Reservation> reserve;
}
