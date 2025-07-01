package com.project.attable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.attable.entity.PayChef;

@Repository
public interface PayChefRepository extends JpaRepository<PayChef, Long> {
	PayChef findByIdIn(long id);
}
