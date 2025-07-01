package com.project.attable.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.project.attable.utils.DateAudit;
import com.project.attable.utils.DateDeserializer;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
@Data
@NoArgsConstructor
public class User extends DateAudit {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "firstName", columnDefinition = "VARCHAR(30)")
	private String firstName;

	@Column(name = "lastName", columnDefinition = "VARCHAR(30)")
	private String lastName;

	@Column(name = "email", columnDefinition = "VARCHAR(50)")
	@Email
	private String email;

	@JsonIgnore
	@Column(name = "password" , columnDefinition = "VARCHAR(100)")
	private String password;

	@Column(name = "gender")
	private Gender gender;

	@JsonDeserialize(using = DateDeserializer.class)
	@Column(name = "birthday")
	private LocalDateTime birthday;

	@Column(name = "registeredDate")
	private LocalDateTime registeredDate = LocalDateTime.now();

	@Column(name = "phoneNumber" , columnDefinition = "VARCHAR(20)")
	private String phoneNumber;

	@Column(name = "city", columnDefinition = "VARCHAR(40)")
	private String city;
	
	@Column(name = "country", columnDefinition = "VARCHAR(40)")
	private String country;

	@Column(name = "nationality", columnDefinition = "VARCHAR(40)")
	private String nationality;

	@LazyCollection(LazyCollectionOption.FALSE)
	@Column(name = "language")
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private Set<Language> language = new HashSet<>();

	@Column(name = "profileImage", columnDefinition = "VARCHAR(255)")
	private String profileImage;

	@Column(name = "isFacebookUser")
	private boolean isFacebookUser;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "status")
    private UserStatus status;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToMany
	@JoinTable(name = "user_allergic", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "allergic_id"))
	private Set<Allergic> allergic;

	@Column(name = "aboutMe", columnDefinition = "TEXT")
	private String aboutMe;

	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
	private Chef chef;

	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Reservation> reserve;

}