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

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.project.attable.utils.DateDeserializer;
import com.project.attable.utils.DateTimeDeserializer;

import lombok.Data;

@Entity
@Table(name = "subevent")
@Data
public class SubEvent {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "subIndex")
	private int subIndex;

	@Column(name = "minSeat")
	private int minSeat;

	@Column(name = "currentSeat")
	private int currentSeat;

	@Column(name = "maxSeat")
	private int maxSeat;

	@Column(name = "startTime", columnDefinition = "VARCHAR(10)")
	private String startTime;

	@Column(name = "endTime", columnDefinition = "VARCHAR(10)")
	private String endTime;

	@Column(name = "reasonCancel", columnDefinition = "TEXT")
	private String reasonCancel;

	@JsonDeserialize(using = DateTimeDeserializer.class)
	@Column(name = "cancelDate")
	private LocalDateTime cancelDate;

	@JsonDeserialize(using = DateDeserializer.class)
	@Column(name = "eventDate")
	private LocalDateTime eventDate;

	@Column(name = "status")
	private EventStatus status;

	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "subevent", cascade = CascadeType.ALL)
	private List<Reservation> reserve;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne(cascade = CascadeType.ALL)
	private Event event;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "subevent_id")
	private List<RejectChefCancel> detailCancelReject;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne(mappedBy = "subEvent", cascade = CascadeType.ALL)
	private PayChef payChef;

}