package com.parcelpulseapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelRequest {
    @NotBlank(message = "Sender name is required")
    private String senderName;

    @NotBlank(message = "Sender address is required")
    private String senderAddress;

    @NotBlank(message = "Receiver name is required")
    private String receiverName;

    @NotBlank(message = "Receiver address is required")
    private String receiverAddress;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be greater than 0")
    private Double weight;

    private String description;
}