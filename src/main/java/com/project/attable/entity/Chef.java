package com.project.attable.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.project.attable.utils.DateTimeDeserializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chef")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chef {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "homeNo", columnDefinition = "VARCHAR(15)")
	private String homeNo;

	@Column(name = "street", columnDefinition = "VARCHAR(40)")
	private String street;

	@Column(name = "subDistrict", columnDefinition = "VARCHAR(40)")
	private String subDistrict;

	@Column(name = "district", columnDefinition = "VARCHAR(40)")
	private String district;

	@Column(name = "postalCode", columnDefinition = "VARCHAR(15)")
	private String postalCode;

	@Column(name = "occupation", columnDefinition = "VARCHAR(50)")
	private String occupation;

	@Column(name = "favoriteCuisine", columnDefinition = "VARCHAR(50)")
	private String favoriteCuisine;

	@Column(name = "signatureDish", columnDefinition = "VARCHAR(50)")
	private String signatureDish;

	@Column(name = "passportId", columnDefinition = "VARCHAR(15)")
	private String passportId;

	@Column(name = "passportUrl", columnDefinition = "VARCHAR(255)")
	private String passportUrl;
	
	@Column(name = "chef_status")
	private ChefStatus status;

	@JsonDeserialize(using = DateTimeDeserializer.class)
	@Column(name = "approveDate")
	private LocalDateTime approveDate;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id", unique = true)
	private Account acc;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "chef_id")
	private List<Experience> exp;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", unique = true)
	private User user;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "chef_id")
	private List<RejectChef> detailReject;

	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "chef", cascade = CascadeType.ALL)
	private List<Event> event;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "chef", cascade = CascadeType.ALL)
	private List<ReviewEvent> reviewEvent;

}
