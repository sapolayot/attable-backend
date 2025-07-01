package com.project.attable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.attable.entity.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

}
