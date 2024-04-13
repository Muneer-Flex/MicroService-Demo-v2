package com.bank.createcustomeraccount.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiExceptionResponse {

    private String errorCode;

    private String errorDescription;

    private String errorCause;

    private LocalDateTime localDateTime;
}
