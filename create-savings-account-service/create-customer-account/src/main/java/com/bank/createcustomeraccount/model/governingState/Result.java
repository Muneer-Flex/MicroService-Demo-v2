package com.bank.createcustomeraccount.model.governingState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result implements Serializable {

    private Status status;

    private List<Error> errors;
}
