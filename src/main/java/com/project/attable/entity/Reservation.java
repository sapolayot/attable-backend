package com.project.attable.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.project.attable.utils.DateDeserializer;
import com.project.attable.utils.DateTimeDeserializer;

import lombok.Data;

@Entity
@Table(name = "reservation")
@Data
public class Reservation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tokenEmail")
    private String tokenEmail;

    @Column(name = "refundable")
    private boolean refundable;

    @JsonDeserialize(using = DateTimeDeserializer.class)
    @Column(name = "reserveDate")
    private LocalDateTime reserveDate;

    @JsonDeserialize(using = DateTimeDeserializer.class)
    @Column(name = "requestingDate")
    private LocalDateTime requestingDate;

    @JsonDeserialize(using = DateTimeDeserializer.class)
    @Column(name = "approveDate")
    private LocalDateTime approveDate;

    @Column(name = "aboutYou")
    private String aboutYou;

    @JsonDeserialize(using = DateTimeDeserializer.class)
    @Column(name = "cancelDate")
    private LocalDateTime cancelDate;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "status")
    private ReserveStatus status;

    @Column(name = "descriptionToKnow", columnDefinition = "TEXT")
    private String descriptionToKnow;

    @Column(name = "seatNumber", columnDefinition = "VARCHAR(40)")
    private String seatNumber;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "subevent_id")
    private SubEvent subevent;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", unique = true)
    private Payment payment;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reserve_id")
    private List<RejectReserve> detailReject;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reserve_id")
    private List<RejectDinerCancel> detailCancelReject;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservationId")
    private List<JoinerEvent> join;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_for_refund_id")
    private AccountForRefund accountForRefund;

    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "reserve")
    private ReviewEvent reviewEvent;

}