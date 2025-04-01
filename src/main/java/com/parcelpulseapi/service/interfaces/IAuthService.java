package com.parcelpulseapi.service.interfaces;

import com.parcelpulseapi.dto.request.AuthRequest.LoginRequest;
import com.parcelpulseapi.dto.request.AuthRequest.RegisterRequest;
import com.parcelpulseapi.dto.response.AuthResponse;
import com.parcelpulseapi.model.User;

public interface IAuthService {
    AuthResponse authenticateUser(LoginRequest loginRequest);
    AuthResponse registerUser(RegisterRequest registerRequest);
    User getCurrentUser();
}