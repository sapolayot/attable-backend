package com.project.attable.entity.response;

import java.util.List;

import com.project.attable.entity.Reservation;
import com.project.attable.entity.User;

import lombok.Data;

@Data
public class DinersResponse {
	private int totalPage;
	private Long totalElement;
	private List<User> diner;
}
