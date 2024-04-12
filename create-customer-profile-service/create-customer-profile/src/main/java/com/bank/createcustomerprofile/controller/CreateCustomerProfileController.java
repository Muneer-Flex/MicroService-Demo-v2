package com.bank.createcustomerprofile.controller;

import com.bank.createcustomerprofile.exception.CreateCustomerProfileException;
import com.bank.createcustomerprofile.model.ApiExceptionResponse;
import com.bank.createcustomerprofile.model.CreateCustomerProfileRequest;
import com.bank.createcustomerprofile.model.CreateCustomerProfileResponse;
import com.bank.createcustomerprofile.service.CreateCustomerProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

@Tag(name = "Create Customer Profile", description = "Create Customer Profile Management API's")
@RestController
@RequestMapping(value = "/private/v1/create")
public class CreateCustomerProfileController {

    private static final Logger logger = LoggerFactory.getLogger(CreateCustomerProfileController.class);

    private final CreateCustomerProfileService createCustomerProfileService;

    public CreateCustomerProfileController(CreateCustomerProfileService createCustomerProfileService) {
        this.createCustomerProfileService = createCustomerProfileService;
    }

    @Operation(description = "Create Customer Profile API", summary = "API to create customer profile")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = CreateCustomerProfileResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ApiExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PostMapping(value = "/customerProfile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<CreateCustomerProfileResponse> createCustomerProfile(@RequestBody @Valid CreateCustomerProfileRequest createCustomerProfileRequest) throws CreateCustomerProfileException {
        logger.info("Received Request to create Profile:: {}", createCustomerProfileRequest);

        CreateCustomerProfileResponse createCustomerProfileResponse = createCustomerProfileService.createCustomerProfile(createCustomerProfileRequest);
        logger.info("Customer Profile Creation Response: {}", createCustomerProfileResponse);

        return new ResponseEntity<>(createCustomerProfileResponse, HttpStatus.CREATED);
    }
}
