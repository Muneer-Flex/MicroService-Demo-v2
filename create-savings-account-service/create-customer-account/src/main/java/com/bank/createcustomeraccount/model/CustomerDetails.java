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
public class CustomerDetails implements Serializable {

    @NotNull
    @NotBlank
    private String customerName;

    @NotNull
    @NotBlank
    private String customerEmailId;

    @NotNull
    @NotBlank
    private String zipCode;
}
