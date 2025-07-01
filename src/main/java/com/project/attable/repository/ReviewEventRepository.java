package com.project.attable.repository;

import com.project.attable.entity.ReviewEvent;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewEventRepository extends JpaRepository<ReviewEvent,Long> {

    ReviewEvent findByIdIn(Long id);

//    Page<ReviewEvent> findByUserId(Pageable pageable,Long userId);
//
//    Page<ReviewEvent> findBySubEventId(Pageable pageable,Long subEventId);
    
    ReviewEvent findByReserveId(Long reserveId);

    Page<ReviewEvent> findAll(Pageable pageable);
    
	Page<ReviewEvent> findByChefId(Pageable pageable, Long chefId);

}
