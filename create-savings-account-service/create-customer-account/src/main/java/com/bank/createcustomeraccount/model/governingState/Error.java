package com.bank.createcustomeraccount.model.governingState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Error implements Serializable {

    private String errorCode;

    private String errorResponse;
}
