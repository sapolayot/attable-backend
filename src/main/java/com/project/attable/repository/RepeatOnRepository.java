package com.project.attable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.attable.entity.Allergic;
import com.project.attable.entity.RepeatOn;

@Repository
public interface RepeatOnRepository extends JpaRepository<RepeatOn, Long> {

}
