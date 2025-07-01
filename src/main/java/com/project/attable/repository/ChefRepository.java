package com.project.attable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.attable.entity.Chef;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Long> {
    Boolean existsByUserEmail(String email); 
    
    Chef findByUserIdIn(Long userId);
    
    Chef findByIdIn(Long chefId);
    
    @Query("Select count(c.id) from Chef c where c.user.createdAt between ?1 and ?2")
    long countChefSignUp(LocalDateTime fromDate, LocalDateTime toDate);
}
