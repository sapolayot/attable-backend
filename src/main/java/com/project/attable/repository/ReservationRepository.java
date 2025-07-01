package com.project.attable.repository;

import com.project.attable.entity.EventStatus;
import com.project.attable.entity.Reservation;
import com.project.attable.entity.ReserveStatus;
import com.project.attable.entity.SubEvent;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	Reservation findByIdIn(Long id);

	List<Reservation> findByStatus(ReserveStatus status);

	@Query("From Reservation r where r.requestingDate > CURDATE() and r.status=0")
	List<Reservation> findCountDown();

	Page<Reservation> findByStatusIn(Pageable pageable, ReserveStatus status);

	Page<Reservation> findByUserIdIn(Pageable pageable, Long id);
	
	List<Reservation> findBySubeventId(Long id);
	
}
