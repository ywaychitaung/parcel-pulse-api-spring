package com.parcelpulseapi.repository;

import com.parcelpulseapi.model.DeliveryPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryPreferenceRepository extends JpaRepository<DeliveryPreference, Long> {
    Optional<DeliveryPreference> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}