package com.project.attable.graphql.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.project.attable.dao.ReviewEventDao;
import com.project.attable.entity.ReviewEvent;
import com.project.attable.entity.response.ReviewResponse;

@Component
public class ReviewEventQuery implements GraphQLQueryResolver {
	@Autowired
	private ReviewEventDao reviewEventDao;

	public ReviewResponse getReviewByChefId(int page, int pagesize,Long chefId) {
		return reviewEventDao.getReviewByChefId(page,pagesize,chefId);
	}
	public ReviewEvent getReviewByReservationId(Long reserveId) {
		return reviewEventDao.getReviewByReservationId(reserveId);
	}
	public ReviewResponse getReviews(int page, int pagesize) {
		return reviewEventDao.getReviews(page, pagesize);
	}
	public ReviewEvent getReviewById(Long reviewId)  {
		return reviewEventDao.getReviewById(reviewId);
	}
}
