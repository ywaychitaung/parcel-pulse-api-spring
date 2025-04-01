package com.parcelpulseapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackingEventRequest {
    @NotBlank(message = "Status is required")
    private String status;

    @NotBlank(message = "Location is required")
    private String location;

    private String description;
}