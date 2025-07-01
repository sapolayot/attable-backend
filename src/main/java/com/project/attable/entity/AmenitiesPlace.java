package com.project.attable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="amenitiesplace")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmenitiesPlace {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="amenitiesPlaceName", columnDefinition = "VARCHAR(60)")
    private String amenitiesPlaceName;
    
}
