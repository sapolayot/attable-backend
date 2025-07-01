package com.project.attable.entity;

import java.time.LocalDateTime;
import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.project.attable.utils.DateDeserializer;
import com.project.attable.utils.DateTimeDeserializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="account_for_refund")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountForRefund {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "accountName", columnDefinition = "VARCHAR(60)")
    private String accountName;
    
    @Column(name="accountNumber", columnDefinition = "VARCHAR(20)")
    private String accountNumber;
    
    @Column(name="bankName", columnDefinition = "VARCHAR(70)")
    private String bankName;
    
    @Column(name="branch", columnDefinition = "VARCHAR(30)")
    private String branch;
    
    @Column(name="bankStatementUrl", columnDefinition = "VARCHAR(255)")
    private String bankStatementUrl;

    @Column(name="slipUrl", columnDefinition = "VARCHAR(255)")
    private String slipUrl;

	@JsonDeserialize(using = DateTimeDeserializer.class)
    @Column(name="dateTimetransfer")
    private LocalDateTime dateTimetransfer;

    @Column(name="text",columnDefinition="TEXT")
    private String text;
    
    @Column(name="amount")
    private double amount;
    
    @Column(name="status")
    private boolean status;
    
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "accountForRefund")
	private Reservation reserve;
    
}
