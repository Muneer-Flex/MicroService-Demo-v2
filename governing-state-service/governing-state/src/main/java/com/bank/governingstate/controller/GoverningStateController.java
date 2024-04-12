package com.bank.governingstate.controller;

import com.bank.governingstate.exception.GoverningStateException;
import com.bank.governingstate.model.GoverningStateExceptionResponse;
import com.bank.governingstate.model.GoverningStateResponse;
import com.bank.governingstate.service.GoverningStateService;
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

@Tag(name = "Governing State", description = "Governing State Management API's")
@RestController
@RequestMapping("/public/v1")
public class GoverningStateController {

    private static final Logger logger = LoggerFactory.getLogger(GoverningStateController.class);

    private final GoverningStateService governingStateService;

    public GoverningStateController(GoverningStateService governingStateService) {
        this.governingStateService = governingStateService;
    }

    @Operation(description = "Fetch Governing State API", summary = "API to fetch the Governing State by ZipCode")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = GoverningStateResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = GoverningStateExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = GoverningStateExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @GetMapping(value = "/fetch/governingState", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GoverningStateResponse> fetchGoverningStateByZipCode(@RequestParam(value = "zipcode", required = true) String zipCode, @RequestHeader(value = "requestId", required = true) String requestId) throws GoverningStateException {
        logger.info("Received request to fetch governing state for {}", zipCode);
        GoverningStateResponse governingStateResponse = governingStateService.fetchGoverningStateByZipcode(zipCode);

        return new ResponseEntity<>(governingStateResponse, HttpStatus.OK);
    }
}
