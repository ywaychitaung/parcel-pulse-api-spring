package com.parcelpulseapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "delivery_preferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DeliveryPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    private String preferredDeliveryTime;
    private String specialInstructions;
    
    @Column(nullable = false)
    private boolean notifyByEmail = false;
    
    @Column(nullable = false)
    private boolean notifyBySMS = false;
    
    private String alternativeAddress;
}