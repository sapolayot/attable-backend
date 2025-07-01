package com.project.attable.repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.attable.entity.Event;
import com.project.attable.entity.EventStatus;



@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    Page<Event> findAll(Pageable pageable);
    
    @Query("From Event e where e.status not in ('11','12')")
    Page<Event> getNotDraftAndDelete(Pageable pageable);
    
    Page<Event> findByStatus(Pageable pageable, EventStatus status);
    
    Optional<Event> findById(Long id);
    
    Event findByIdIn(Long id);

	List<Event> findByStatus(EventStatus status);
	
	@Query("select e from Event e join e.subevent s where s.eventDate = CURDATE() and e.status=?1")
	List<Event> findByStatusAndCurrentDate(EventStatus status);
}
