package com.parcelpulseapi.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPreferenceRequest {
    @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Preferred delivery time must be in HH:mm format")
    private String preferredDeliveryTime;
    
    private String specialInstructions;
    private boolean notifyByEmail;
    private boolean notifyBySMS;
    private String alternativeAddress;
}