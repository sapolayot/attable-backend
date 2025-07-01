package com.project.attable.repository;

import com.project.attable.entity.ContactStatus;
import com.project.attable.entity.ContactUs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactUsRepository extends JpaRepository <ContactUs,Long> {

    ContactUs findByIdIn(Long id);

    Page<ContactUs> findByContactStatus(Pageable pageable, ContactStatus contact);

    Page<ContactUs> findByEmailIsContaining(Pageable pageable,String email);

    Page<ContactUs> findByNameIsContaining(Pageable pageable,String name);

    
}
