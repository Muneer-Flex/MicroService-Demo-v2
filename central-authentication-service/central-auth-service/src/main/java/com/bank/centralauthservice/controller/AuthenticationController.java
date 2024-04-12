package com.bank.centralauthservice.controller;

import com.bank.centralauthservice.model.ApiExceptionResponse;
import com.bank.centralauthservice.model.AuthenticationRequest;
import com.bank.centralauthservice.model.AuthenticationResponse;
import com.bank.centralauthservice.service.AuthenticationService;
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

@Tag(name = "Authentication API Controller", description = "Authentication Management API's")
@RestController
@RequestMapping("/public/v1/auth")
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(description = "API to generate JWT Token", summary = "This API authenticates the user and generate the JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = AuthenticationResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema(implementation = ApiExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ApiExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PostMapping(value = "/generateJwt", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> generateJwt(@RequestBody @Valid AuthenticationRequest authenticationRequest) throws Exception {
        logger.info("Received Request to generate JWT for user: {} to access: {}", authenticationRequest.getUserName(), authenticationRequest.getAccessRequestedFor());

        AuthenticationResponse authenticationResponse = authenticationService.authenticateUser(authenticationRequest);
        logger.info("Authentication Response:: {}", authenticationResponse);

        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }
}
