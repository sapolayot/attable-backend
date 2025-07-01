package com.project.attable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "media")
@Data
public class Media {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="type", columnDefinition = "VARCHAR(20)")
    private String type;
    
    @Column(name="path", columnDefinition = "VARCHAR(255)")
    private String path;

}
