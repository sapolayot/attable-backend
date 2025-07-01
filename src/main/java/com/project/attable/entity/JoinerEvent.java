package com.project.attable.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;

@Entity
@Table(name="joinerevent")
@Data
public class JoinerEvent {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="firstName", columnDefinition = "VARCHAR(30)")
    private String firstName;
    
    @Column(name="lastName", columnDefinition = "VARCHAR(30)")
    private String lastName;
    
    @LazyCollection(LazyCollectionOption.FALSE)
    @OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToMany
	@JoinTable(name = "joinerevent_allergic", 
		joinColumns = @JoinColumn(name = "joinerevent_id"), 
		inverseJoinColumns = @JoinColumn(name = "allergic_id"))
	private List<Allergic> allergic;    
    
}
