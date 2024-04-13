package com.bank.createcustomeraccount.entity;

import com.bank.createcustomeraccount.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CUSTOMER_ACCOUNT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerAccount implements Serializable {

    @Id
    @SequenceGenerator(name = "accountSequence", sequenceName = "CUSTOMER_ACCOUNT_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountSequence")
    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Column(name = "CUSTOMER_NAME", nullable = false)
    private String customerName;

    @Column(name = "ACCOUNT_NUMBER", nullable = false)
    private String accountNumber;

    @Column(name = "ACCOUNT_TYPE", nullable = false)
    private AccountType accountType;

    @CreationTimestamp
    @Column(name = "CREATED_ON", nullable = false)
    private Date createdOn;
}
