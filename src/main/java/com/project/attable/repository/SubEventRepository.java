package com.project.attable.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

import com.project.attable.entity.EventStatus;
import com.project.attable.entity.SubEvent;

public interface SubEventRepository extends JpaRepository<SubEvent, Long> {
	SubEvent findByIdIn(Long id);

	List<SubEvent> findByStatus(EventStatus status);
	
	@Query("From SubEvent s where s.eventDate = CURDATE() and s.status = ?1")
	List<SubEvent> findByStatusAndCurrentDate(EventStatus status);
	
	Page<SubEvent> findByStatus(Pageable pageable,EventStatus status);

	@Query("Select count(s.id) from SubEvent s join s.event e where e.createdAt between ?1 and ?2")
	long countEventRegistered(LocalDateTime fromDate, LocalDateTime toDate);
	
	@Query("Select count(s.id) from SubEvent s join s.event e where e.createdAt between ?1 and ?2 and s.status=?3")
	long countByStatus(LocalDateTime fromDate, LocalDateTime toDate, EventStatus status);
	
	@Query("From SubEvent s where s.status not in ('11','12')")
	Page<SubEvent> getNotDraftAndDelete(PageRequest pageRequest);
	
	@Query("Select COALESCE(sum(s.event.priceOriginal),0) from SubEvent s join s.reserve r join r.join where s.event.createdAt between ?1 and ?2 and s.status='7'")
	long sumChefCharge(LocalDateTime fromDate, LocalDateTime toDate);

	@Query("Select COALESCE(sum(s.event.pricePerSeat),0) from SubEvent s join s.reserve r join r.join where s.event.createdAt between ?1 and ?2 and s.status='7'")
	long sumAttableFee(LocalDateTime fromDate, LocalDateTime toDate);
}
