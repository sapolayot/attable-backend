package com.project.attable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.attable.entity.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

}
