package com.example.UserManagementt.service;

import com.example.UserManagementt.dto.LoginRequest;
import com.example.UserManagementt.dto.LoginResponse;
import com.example.UserManagementt.dto.RegisterRequest;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    String register(RegisterRequest request);
}
