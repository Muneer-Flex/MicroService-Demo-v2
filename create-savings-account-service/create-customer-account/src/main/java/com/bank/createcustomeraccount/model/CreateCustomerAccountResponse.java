package com.bank.createcustomeraccount.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerAccountResponse implements Serializable {

    private String customerName;

    private String customerAccountNumber;

    private Long customerId;

    private AccountType accountType;

    private LocalDateTime createdOn;
}
