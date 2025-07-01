package com.project.attable.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class ReviewEvent {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "rate1")
    private int rate1;
    
    @Column(name = "rate2")
    private int rate2;
    
    @Column(name = "rate3")
    private int rate3;
    
    @Column(name = "rate4")
    private int rate4;
    
    @Column(name = "rate5")
    private int rate5;
    
    @Column(name = "rate6")
    private int rate6;
    
    @Column(name = "rate7")
    private int rate7;
    
    @Column(name = "commentDate")
    private LocalDateTime commentDate;
    
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment; 
      
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="reserve_id", unique = true)
    private Reservation reserve;
    
	@JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="chef_id")
    private Chef chef;
}
