package com.bank.createcustomerprofile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerProfileRequest {

    @NotNull
    private String customerName;

    @NotNull
    private String customerEmailId;

    @NotNull
    private String customerGoverningState;
}
