package com.parcelpulseapi.repository;

import com.parcelpulseapi.model.TrackingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrackingEventRepository extends JpaRepository<TrackingEvent, Long> {
    List<TrackingEvent> findByParcelId(Long parcelId);
    List<TrackingEvent> findByParcelIdOrderByCreatedAtDesc(Long parcelId);
    List<TrackingEvent> findByParcelIdAndCreatedAtBetween(Long parcelId, LocalDateTime startDate, LocalDateTime endDate);
    List<TrackingEvent> findByParcelIdAndStatus(Long parcelId, String status);
    boolean existsByParcelIdAndStatus(Long parcelId, String status);
}