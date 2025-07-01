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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.project.attable.utils.DateAudit;
import com.project.attable.utils.DateDeserializer;
import com.project.attable.utils.DateTimeDeserializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="event")
@NoArgsConstructor
@AllArgsConstructor
public class Event extends DateAudit{
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="eventName", columnDefinition = "VARCHAR(200)")
	private String eventName;

	@Column(name="eventType")
	private EventType eventType;

	@Column(name="eventCategory")
	private EventCategory eventCategory;
	
	@Column(name="eventDetail", columnDefinition = "TEXT")
	private String eventDetail;
	
	@Column(name="status")
	private EventStatus status;
	
	@LazyCollection(LazyCollectionOption.FALSE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private List<RejectEvent> detailReject;
	
	@Column(name="pricePerSeat")
	private double pricePerSeat;
	
	@Column(name="priceOriginal")
	private double priceOriginal;
	
	@Column(name="lat")
	private double lat;
	
	@Column(name="lng")
	private double lng;
	
	@Column(name="commission")
	private double commission;
	
	@Column(name="commissionType")
	private CommissionType commissionType;
	
    @Column(name = "homeNo", columnDefinition = "VARCHAR(15)")
    private String homeNo;

    @Column(name = "street", columnDefinition = "VARCHAR(40)")
    private String street;

    @Column(name = "subDistrict", columnDefinition = "VARCHAR(40)")
    private String subDistrict;

    @Column(name = "district", columnDefinition = "VARCHAR(40)")
    private String district;
    
    @Column(name = "city", columnDefinition = "VARCHAR(40)")
    private String city;

    @Column(name = "postalCode", columnDefinition = "VARCHAR(10)")
    private String postalCode;
	
	@JsonDeserialize(using = DateDeserializer.class)
	@Column(name = "startDate")
	private LocalDateTime startDate;
	
	@JsonDeserialize(using = DateDeserializer.class)
	@Column(name = "endDate")
	private LocalDateTime endDate;
		
	@LazyCollection(LazyCollectionOption.FALSE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="event_id")
	private List<RepeatOn> repeatOn;
	
	@Column(name="rule", columnDefinition = "TEXT")
	private String rule;
	
	@Column(name="ownPlace")
	private boolean ownPlace;
	
	@JsonDeserialize(using = DateDeserializer.class)
	@Column(name="requestingDate")
	private LocalDateTime requestingDate;
	
    @JsonDeserialize(using = DateTimeDeserializer.class)
	@Column(name="approveDate")
	private LocalDateTime approveDate;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToMany
	@JoinTable(name = "event_allergic", 
		joinColumns = @JoinColumn(name = "event_id"), 
		inverseJoinColumns = @JoinColumn(name = "allergic_id"))
	private List<Allergic> allergic;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="event_id")
	private List<SubEvent> subevent;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="event_id")
	private List<Media> media;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="event_id")
	private List<Menu> menu;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="event_id")
	private List<AmenitiesPlace> amenitiesPlace;
	
	 
	@LazyCollection(LazyCollectionOption.FALSE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="chef_id")
	private Chef chef;


}