package com.parcelpulseapi.service.implementation;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.parcelpulseapi.dto.request.AuthRequest.LoginRequest;
import com.parcelpulseapi.dto.request.AuthRequest.RegisterRequest;
import com.parcelpulseapi.dto.response.AuthResponse;
import com.parcelpulseapi.exception.BadRequestException;
import com.parcelpulseapi.exception.ResourceNotFoundException;
import com.parcelpulseapi.model.Role;
import com.parcelpulseapi.model.RoleName;
import com.parcelpulseapi.model.User;
import com.parcelpulseapi.repository.RoleRepository;
import com.parcelpulseapi.repository.UserRepository;
import com.parcelpulseapi.security.JwtTokenProvider;
import com.parcelpulseapi.security.UserPrincipal;
import com.parcelpulseapi.service.interfaces.IAuthService;

@Service
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    private String getUserRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .findFirst()
                .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                .orElse("USER");
    }

    @Override
    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        String role = getUserRole(authentication);
        return new AuthResponse(jwt, role);
    }

    @Override
    public AuthResponse registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new BadRequestException("Username is already taken!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email is already in use!");
        }
    
        // Validate roleId
        Long roleId = registerRequest.getRoleId();
        if (roleId != 1 && roleId != 3) {
            throw new BadRequestException("Invalid role selection. Only User (1) or Courier (3) roles are allowed.");
        }
    
        User user = new User(
                registerRequest.getName(),
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword())
        );
    
        // Map roleId to RoleName
        RoleName roleName = (roleId == 1) ? RoleName.ROLE_USER : RoleName.ROLE_COURIER;
        
        Role userRole = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found."));
    
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
    
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getUsername(),
                        registerRequest.getPassword()
                )
        );
    
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        String role = getUserRole(authentication);
        return new AuthResponse(jwt, role);
    }

    @Override
    public User getCurrentUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}
