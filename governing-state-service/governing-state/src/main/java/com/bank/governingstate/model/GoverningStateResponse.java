package com.bank.governingstate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoverningStateResponse implements Serializable {

    private String zipCode;

    private String governingState;
}
