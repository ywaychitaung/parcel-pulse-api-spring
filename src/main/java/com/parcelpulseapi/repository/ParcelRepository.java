package com.parcelpulseapi.repository;

import com.parcelpulseapi.model.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    List<Parcel> findByUserId(Long userId);
    Optional<Parcel> findByTrackingNumber(String trackingNumber);
    List<Parcel> findByStatus(String status);
    List<Parcel> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Parcel> findByStatusAndCreatedAtBetween(String status, LocalDateTime startDate, LocalDateTime endDate);
    boolean existsByTrackingNumber(String trackingNumber);
}