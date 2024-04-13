package com.bank.createcustomeraccount.controller;

import com.bank.createcustomeraccount.exception.*;
import com.bank.createcustomeraccount.model.ApiExceptionResponse;
import com.bank.createcustomeraccount.model.CreateCustomerAccountRequest;
import com.bank.createcustomeraccount.model.CreateCustomerAccountResponse;
import com.bank.createcustomeraccount.service.CreateCustomerAccountService;
import com.bank.createcustomeraccount.service.helper.AppUtils;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Create Customer Account", description = "Create Customer Account Management API's")
@RestController
@RequestMapping(value = "/public/v1/customerAccount")
public class CreateCustomerAccountController {

    private static final Logger logger = LoggerFactory.getLogger(CreateCustomerAccountController.class);

    private final CreateCustomerAccountService createCustomerAccountService;

    public CreateCustomerAccountController(CreateCustomerAccountService createCustomerAccountService) {
        this.createCustomerAccountService = createCustomerAccountService;
    }

    @Operation(description = "Create Customer Account", summary = "API to create customer account details")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = CreateCustomerAccountResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateCustomerAccountResponse> createCustomerAccount(@RequestBody @Valid CreateCustomerAccountRequest createCustomerAccountRequest, @RequestHeader(value = "requestId", required = true) String requestId) throws GoverningStateServiceException, CentralAuthServiceException, CreateCustomerProfileServiceException, EurekaServiceException, CreateCustomerAccountException, IntegrationException {
        logger.info("Request Received to create customer account:: {}", AppUtils.convertToJson(createCustomerAccountRequest));

        CreateCustomerAccountResponse createCustomerAccountResponse = createCustomerAccountService.createCustomerAccount(createCustomerAccountRequest, requestId);
        logger.info("Customer Account Creation Response:: {}", AppUtils.convertToJson(createCustomerAccountResponse));

        return new ResponseEntity<>(createCustomerAccountResponse, HttpStatus.CREATED);
    }
}
