package com.project.attable.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="experience")
@Data
public class Experience {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="cuisineType", columnDefinition = "VARCHAR(30)")
    private String cuisineType;
    
    @Column(name="expYear", columnDefinition = "VARCHAR(30)")
    private String expYear;
    
    @Column(name="aboutExp", columnDefinition = "TEXT")
    private String aboutExp;
    
}
