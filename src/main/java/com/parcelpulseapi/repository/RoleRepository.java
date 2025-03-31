package com.parcelpulseapi.repository;

import com.parcelpulseapi.model.Role;
import com.parcelpulseapi.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
    
    // Add this method
    Boolean existsByName(RoleName roleName);
}