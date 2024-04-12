package com.bank.createcustomerprofile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerProfileResponse {

    private Status status;

    private LocalDateTime createdOn;
}
