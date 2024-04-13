package com.bank.createcustomeraccount.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerAccountRequest implements Serializable {

    @NotNull
    private CustomerDetails customerDetails;

    @NotNull
    private AccountDetails accountDetails;
}
