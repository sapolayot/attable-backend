package com.project.attable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "accountName", columnDefinition = "VARCHAR(60)")
    private String accountName;
    
    @Column(name="accountNumber", columnDefinition = "VARCHAR(20)")
    private String accountNumber;
    
    @Column(name="bankName", columnDefinition = "VARCHAR(100)")
    private String bankName;
    
    @Column(name="branch", columnDefinition = "VARCHAR(30)")
    private String branch;
    
    @Column(name="bankStatementUrl", columnDefinition = "VARCHAR(255)")
    private String bankStatementUrl;
    
}
