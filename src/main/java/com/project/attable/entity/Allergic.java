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
@Table(name="allergic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Allergic {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="allergicName", columnDefinition = "VARCHAR(50)")
    private String allergicName;
    
	public Allergic(String allergicName) {
		this.allergicName = allergicName;
	}
    
    
}
