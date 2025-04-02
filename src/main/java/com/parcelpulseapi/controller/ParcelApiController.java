package com.parcelpulseapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.parcelpulseapi.dto.request.ParcelRequest;
import com.parcelpulseapi.dto.response.ParcelResponse;
import com.parcelpulseapi.service.interfaces.IParcelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/parcels")
@Tag(name = "Parcels", description = "Parcel Management API")
@SecurityRequirement(name = "bearerAuth")
@ApiResponses(value = {
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden"),
    @ApiResponse(responseCode = "404", description = "Resource not found")
})
public class ParcelApiController {
    
    private final IParcelService parcelService;

    public ParcelApiController(IParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @PostMapping
    @Operation(summary = "Create a new parcel", description = "Create a new parcel with the given details")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")  // Update this line
    public ResponseEntity<?> createParcel(@Valid @RequestBody ParcelRequest request) {
        return ResponseEntity.ok(parcelService.createParcel(request));
    }

    @GetMapping
    @Operation(summary = "Get all parcels", description = "Get a list of all parcels (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ParcelResponse>> getAllParcels() {
        return ResponseEntity.ok(parcelService.getAllParcels());
    }

    @GetMapping("/user")
    @Operation(summary = "Get user parcels", description = "Get a list of parcels for the current user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<ParcelResponse>> getUserParcels() {
        return ResponseEntity.ok(parcelService.getCurrentUserParcels());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get parcel by ID", description = "Get a specific parcel by its ID")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ParcelResponse> getParcel(
            @Parameter(description = "Parcel ID", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(parcelService.getParcel(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update parcel", description = "Update an existing parcel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParcelResponse> updateParcel(
            @Parameter(description = "Parcel ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody ParcelRequest request) {
        return ResponseEntity.ok(parcelService.updateParcel(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete parcel", description = "Delete an existing parcel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteParcel(
            @Parameter(description = "Parcel ID", required = true)
            @PathVariable Long id) {
        parcelService.deleteParcel(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tracking/{trackingNumber}")
    @Operation(summary = "Get parcel by tracking number", description = "Get a specific parcel by its tracking number")
    // Remove the @PreAuthorize annotation to make it public
    public ResponseEntity<ParcelResponse> getParcelByTrackingNumber(
            @Parameter(description = "Tracking Number", required = true)
            @PathVariable String trackingNumber) {
        return ResponseEntity.ok(parcelService.getParcelByTrackingNumber(trackingNumber));
    }

    @GetMapping("/search")
    @Operation(summary = "Search parcels", description = "Search parcels by status or date range")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ParcelResponse>> searchParcels(
            @Parameter(description = "Parcel Status")
            @RequestParam(required = false) String status,
            @Parameter(description = "Start Date (yyyy-MM-dd)")
            @RequestParam(required = false) String startDate,
            @Parameter(description = "End Date (yyyy-MM-dd)")
            @RequestParam(required = false) String endDate) {
        return ResponseEntity.ok(parcelService.searchParcels(status, startDate, endDate));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationExceptions(jakarta.validation.ConstraintViolationException ex) {
        return ResponseEntity
            .badRequest()
            .body("Validation error: " + ex.getMessage());
    }
}