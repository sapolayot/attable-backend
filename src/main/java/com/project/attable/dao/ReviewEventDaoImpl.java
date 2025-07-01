package com.project.attable.dao;

import com.project.attable.entity.Chef;
import com.project.attable.entity.Reservation;
import com.project.attable.entity.ReviewEvent;
import com.project.attable.entity.response.ReviewResponse;
import com.project.attable.repository.ChefRepository;
import com.project.attable.repository.ReservationRepository;
import com.project.attable.repository.ReviewEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
@Repository
public class ReviewEventDaoImpl implements ReviewEventDao {

    @Autowired
    ReviewEventRepository reviewEventRepository;
    
    @Autowired
    ReservationRepository reservationRepository;
    
    @Autowired
    ChefRepository chefRepository;

    @Override
    public ReviewEvent createReviewEvent(ReviewEvent reviewEvent, Long reserveId,Long chefId) {
    	Reservation reserve = reservationRepository.findByIdIn(reserveId);
    	Chef chef = chefRepository.findByIdIn(chefId);
    	reviewEvent.setCommentDate(LocalDateTime.now());
    	reviewEvent.setReserve(reserve);
    	reviewEvent.setChef(chef);
    	reviewEvent.setReserve(reserve);
    	return reviewEventRepository.save(reviewEvent);
    }
    
    @Override
    public ReviewEvent getReviewByReservationId(Long reserveId) {
        ReviewEvent reviewEvent = reviewEventRepository.findByReserveId(reserveId);
        return reviewEvent;
    }

    @Override
    public ReviewResponse getReviews(int page, int pagesize) {
        PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
        Page<ReviewEvent> reviewevents = reviewEventRepository.findAll(pageRequest);
        ReviewResponse response = new ReviewResponse();
        response.setTotalPage(reviewevents.getTotalPages());
        response.setTotalElement(reviewevents.getTotalElements());
        response.setReviewevents(reviewevents.getContent());
        return response;
    }

    @Override
    public ReviewEvent getReviewById(Long reviewId) {
        ReviewEvent review = reviewEventRepository.findByIdIn(reviewId);
        return review;
    }

	@Override
	public ReviewResponse getReviewByChefId(int page, int pagesize,Long chefId) {
		PageRequest pageRequest = PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "id"));
        Page<ReviewEvent> reviewevents = reviewEventRepository.findByChefId(pageRequest,chefId);
        ReviewResponse response = new ReviewResponse();
        response.setTotalPage(reviewevents.getTotalPages());
        response.setTotalElement(reviewevents.getTotalElements());
        response.setReviewevents(reviewevents.getContent());
        return response;
	}
}
