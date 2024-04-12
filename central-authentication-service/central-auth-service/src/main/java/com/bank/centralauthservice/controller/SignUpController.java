package com.bank.centralauthservice.controller;

import com.bank.centralauthservice.exception.UserSignUpException;
import com.bank.centralauthservice.model.ApiExceptionResponse;
import com.bank.centralauthservice.model.AuthenticationResponse;
import com.bank.centralauthservice.model.SignUpRequest;
import com.bank.centralauthservice.model.SignUpResponse;
import com.bank.centralauthservice.service.SignUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "SignUp API Controller", description = "User SignUp Management API's")
@RestController
@RequestMapping("/public/v1/user")
public class SignUpController {
    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    private final SignUpService signUpService;

    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @Operation(description = "API to register the user", summary = "This API registers the user into DB")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = SignUpResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PostMapping(value = "/signUp", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SignUpResponse> userSignUp(@RequestBody @Valid SignUpRequest signUpRequest) throws UserSignUpException {
        logger.info("Received User SignUp Request for {}", signUpRequest.getUserName());
        SignUpResponse signUpResponse = signUpService.registerUser(signUpRequest);

        logger.info("User SignUp Response:: {}", signUpResponse);
        return new ResponseEntity<>(signUpResponse, HttpStatus.CREATED);
    }
}
