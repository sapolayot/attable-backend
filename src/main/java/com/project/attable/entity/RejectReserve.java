package com.project.attable.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.project.attable.utils.DateTimeDeserializer;

import lombok.Data;

@Data
@Entity
@Table(name="RejectReserve")
public class RejectReserve {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "rejectTitle", columnDefinition = "VARCHAR(100)")
    private String rejectTitle;
    
    @Column(name = "rejectText", columnDefinition = "TEXT")
    private String rejectText;
    
    @JsonDeserialize(using = DateTimeDeserializer.class)
	@Column(name="rejectDate")
    private LocalDateTime rejectDate;
}
