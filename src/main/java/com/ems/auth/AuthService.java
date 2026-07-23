package com.ems.auth;

public interface AuthService{
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
