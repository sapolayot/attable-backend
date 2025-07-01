package com.project.attable.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.project.attable.utils.DateDeserializer;
import com.project.attable.utils.DateTimeDeserializer;

import lombok.Data;

@Data
@Entity
@Table(name="PayChef")
public class PayChef {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="slipUrl", columnDefinition = "VARCHAR(255)")
    private String slipUrl;
    
    @JsonDeserialize(using = DateTimeDeserializer.class)
    @Column(name="dateTimeTransfer")
    private LocalDateTime dateTimeTransfer;
    
    @JsonDeserialize(using = DateDeserializer.class)
    @Column(name="transactionDate")
    private LocalDateTime transactionDate;
    
    @Column(name="text", columnDefinition = "TEXT")
    private String text;
    
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="subevent_id", unique = true)
    private SubEvent subEvent;
    
}
