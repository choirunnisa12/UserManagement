package com.example.UserManagementt.service.impl;

import com.example.UserManagementt.dto.LoginRequest;
import com.example.UserManagementt.dto.LoginResponse;
import com.example.UserManagementt.dto.RegisterRequest;
import com.example.UserManagementt.entity.User;
import com.example.UserManagementt.repository.UserRepository;
import com.example.UserManagementt.security.JwtUtil;
import com.example.UserManagementt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponse(token, "Login Successful");
    }

    @Override
    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())){
            throw  new RuntimeException("email aready registered");
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
        return "User register successfully";
    }

}
