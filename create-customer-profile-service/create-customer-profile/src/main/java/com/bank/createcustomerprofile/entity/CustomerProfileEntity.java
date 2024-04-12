package com.bank.createcustomerprofile.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CUSTOMER_PROFILE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerProfileEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "customerSequence", sequenceName = "CUSTOMER_PROFILE_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerSequence")
    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Column(name = "CUSTOMER_NAME", nullable = false)
    private String customerName;

    @Column(name = "CUSTOMER_EMAIL", nullable = false)
    private String customerEmail;

    @Column(name = "GOVERNING_STATE", nullable = false)
    private String governingState;

    @CreationTimestamp
    @Column(name = "CREATED_ON", nullable = false)
    private Date createdOn;
}


