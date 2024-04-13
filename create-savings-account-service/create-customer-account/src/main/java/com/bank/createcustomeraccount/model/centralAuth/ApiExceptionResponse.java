package com.bank.createcustomeraccount.model.centralAuth;

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

    private LocalDateTime localDateTime;
}
