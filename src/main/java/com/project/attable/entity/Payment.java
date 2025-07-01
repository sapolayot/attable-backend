package com.project.attable.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.project.attable.utils.DateDeserializer;
import com.project.attable.utils.DateTimeDeserializer;

import lombok.Data;

@Table(name="payment")
@Entity
@Data
public class Payment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="paymentMethod")
    private PaymentMethod paymentMethod;
    
    @JsonDeserialize(using = DateTimeDeserializer.class)
    @Column(name="paymentDate")
    private LocalDateTime paymentDate; 
    
    @Column(name="transactionTime")
    private LocalDateTime transactionTime; 
    
    //CREDIT_CARD
    @Column(name="refId", columnDefinition = "VARCHAR(30)")
    private String refId;
    
    //BANK_TRANSFER
    @Column(name="bankName", columnDefinition = "VARCHAR(70)")
    private String bankName;
    
    @Column(name="price")
    private double price;
    
    @Column(name="slipUrl", columnDefinition = "VARCHAR(255)")
    private String slipUrl;

    @Column(name="docUrl", columnDefinition = "VARCHAR(255)")
    private String docUrl;

    @Column(name="text", columnDefinition = "TEXT")
    private String text;
    
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(mappedBy = "payment",cascade = CascadeType.MERGE)
    private Reservation reserve;

}
