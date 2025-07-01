package com.project.attable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="submenu")
@Data
public class SubMenu {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "subMenuName", columnDefinition = "VARCHAR(100)")
    private String subMenuName;
    
    @Column(name = "subMenuDescription" , columnDefinition = "TEXT")
    private String subMenuDescription;
       
}
