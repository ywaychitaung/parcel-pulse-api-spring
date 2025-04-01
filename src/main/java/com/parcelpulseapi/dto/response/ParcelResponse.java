package com.parcelpulseapi.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelResponse {
    private Long id;
    private String trackingNumber;
    private String senderName;
    private String senderAddress;
    private String receiverName;
    private String receiverAddress;
    private String status;
    private Double weight;
    private String description;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}