package com.project.attable.entity.response;

import java.util.List;

import com.project.attable.entity.ReviewEvent;

import lombok.Data;

@Data
public class ReviewResponse {
	private int totalPage;
	private Long totalElement;
	private List<ReviewEvent> reviewevents;
}
