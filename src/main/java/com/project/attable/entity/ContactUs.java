package com.project.attable.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class ContactUs {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", columnDefinition = "VARCHAR(50)")
    private String name; 
    
    @Column(name="email", columnDefinition = "VARCHAR(40)")
    @Email
    private String email;
    
    @Column(name="phoneNumber", columnDefinition = "VARCHAR(20)")
    private String phoneNumber;
    
    @Column(name="subject", columnDefinition = "VARCHAR(100)")
    private String subject;
    
    @Column(name="messages", columnDefinition = "TEXT")
    private String messages;
    
    @Column(name="contactDate")
    private Date contactDate;
    
    @Column(name="contactStatus")
    private ContactStatus contactStatus;
    
    @Column(name="replyId")
    private Long mainContactId;
    
    @Column(name="answer")
    private boolean answer;
}
