package com.parcelpulseapi.config;

import com.parcelpulseapi.model.Role;
import com.parcelpulseapi.model.RoleName;
import com.parcelpulseapi.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles
        if (roleRepository.count() == 0) {
            logger.info("Initializing roles in the database");
            
            Arrays.stream(RoleName.values()).forEach(roleName -> {
                // Check if role exists by finding it first
                if (roleRepository.findByName(roleName).isEmpty()) {
                    Role role = new Role(roleName);
                    roleRepository.save(role);
                    logger.info("Created role: {}", roleName);
                }
            });
            
            logger.info("Role initialization completed");
        } else {
            logger.info("Roles already exist in the database");
        }
    }
}