package com.project.attable.dao;

import com.project.attable.entity.ReviewEvent;
import com.project.attable.entity.response.ReviewResponse;

public interface ReviewEventDao {
	
	ReviewEvent createReviewEvent(ReviewEvent reviewEvent, Long reserveId, Long chefId);

	ReviewEvent getReviewByReservationId(Long reserveId);
	
	ReviewResponse getReviewByChefId(int page, int pagesize,Long chefId);
	
    ReviewResponse getReviews (int page, int pagesize);

    ReviewEvent getReviewById (Long reviewId);
}
