package com.bank.governingstate.exception.handler;

import com.bank.governingstate.constants.ErrorConstants;
import com.bank.governingstate.exception.GlobalException;
import com.bank.governingstate.exception.GoverningStateException;
import com.bank.governingstate.model.Error;
import com.bank.governingstate.model.GoverningStateExceptionResponse;
import com.bank.governingstate.model.Result;
import com.bank.governingstate.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = GoverningStateException.class)
    public ResponseEntity<GoverningStateExceptionResponse> handleGoverningStateException(GlobalException globalException) {
        Error error = Error.builder()
                .errorCode(globalException.getErrorCode())
                .errorResponse(globalException.getMessage())
                .build();

        GoverningStateExceptionResponse governingStateExceptionResponse = GoverningStateExceptionResponse.builder()
                .result(buildResult(Status.FAILURE, Arrays.asList(error)))
                .build();

        return new ResponseEntity<>(governingStateExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {MissingRequestHeaderException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<GoverningStateExceptionResponse> handleBadRequestException(Exception exception){
        logger.error("Bad Request Exception. Exception is:: {}", exception.getMessage());
        Error error = Error.builder()
                .errorCode(ErrorConstants.GS_003)
                .errorResponse(exception.getMessage())
                .build();

        GoverningStateExceptionResponse governingStateExceptionResponse = GoverningStateExceptionResponse.builder()
                .result(buildResult(Status.FAILURE, Arrays.asList(error)))
                .build();

        return new ResponseEntity<>(governingStateExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    private Result buildResult(Status status, List<Error> errors) {
        return Result.builder()
                .status(status)
                .errors(errors)
                .build();
    }
}
