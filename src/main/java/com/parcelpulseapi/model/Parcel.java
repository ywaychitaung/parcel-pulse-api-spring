package com.parcelpulseapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parcels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String trackingNumber;

    @Column(nullable = false)
    private String senderName;

    @Column(nullable = false)
    private String senderAddress;

    @Column(nullable = false)
    private String receiverName;

    @Column(nullable = false)
    private String receiverAddress;

    @Column(nullable = false)
    private String status = "PENDING";

    @Column(nullable = false)
    private Double weight;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "parcel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrackingEvent> trackingEvents = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}