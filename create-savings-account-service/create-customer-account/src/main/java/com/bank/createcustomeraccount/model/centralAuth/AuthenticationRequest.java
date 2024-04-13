package com.bank.createcustomeraccount.model.centralAuth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {

    @NotNull
    private String userName;

    @NotNull
    private String password;

    @NotBlank
    private String accessRequestedFor;
}
