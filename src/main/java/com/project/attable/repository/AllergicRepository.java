package com.project.attable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.attable.entity.Allergic;

@Repository
public interface AllergicRepository extends JpaRepository<Allergic, Long> {

}
