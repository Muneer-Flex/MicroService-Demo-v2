package com.bank.centralauthservice.exception;

import com.bank.centralauthservice.constants.ErrorConstants;
import com.bank.centralauthservice.model.ApiExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> handleBadRequestException(MethodArgumentNotValidException methodArgumentNotValidException) {
        logger.error("Bad Request - Required Fields in Payload is not present");

        StringBuffer errorDescription = new StringBuffer();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> {
            errorDescription.append("Request Field - ");
            errorDescription.append(((FieldError) error).getField());
            errorDescription.append(" ");
            errorDescription.append(error.getDefaultMessage());
            errorDescription.append(";");
        });

        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .errorCode(ErrorConstants.AUTH_008)
                .errorDescription(errorDescription.toString())
                .localDateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleUserNameNotFoundException(UsernameNotFoundException usernameNotFoundException) {
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .errorCode(ErrorConstants.AUTH_001)
                .errorDescription(usernameNotFoundException.getMessage())
                .localDateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiExceptionResponse> handleBadCredentialsException(BadCredentialsException badCredentialsException) {
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .errorCode(ErrorConstants.AUTH_003)
                .errorDescription(badCredentialsException.getMessage())
                .localDateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ApiExceptionResponse> handleUnAuthorizedException(UnAuthorizedException unAuthorizedException) {
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .errorCode(unAuthorizedException.getErrorCode())
                .errorDescription(unAuthorizedException.getMessage())
                .localDateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<ApiExceptionResponse> handleForBiddenAccessException(ForbiddenAccessException forbiddenAccessException) {
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .errorCode(forbiddenAccessException.getErrorCode())
                .errorDescription(forbiddenAccessException.getMessage())
                .localDateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JwtSecretKeyNotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleJwtSecretKeyNotFoundException(JwtSecretKeyNotFoundException jwtSecretKeyNotFoundException) {
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .errorCode(jwtSecretKeyNotFoundException.getErrorCode())
                .errorDescription(jwtSecretKeyNotFoundException.getMessage())
                .localDateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserSignUpException.class)
    public ResponseEntity<ApiExceptionResponse> handleUserSignUpException(UserSignUpException userSignUpException) {
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .errorCode(userSignUpException.getErrorCode())
                .errorDescription(userSignUpException.getMessage())
                .localDateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
