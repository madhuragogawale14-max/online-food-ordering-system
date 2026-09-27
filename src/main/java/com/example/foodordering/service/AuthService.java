package com.example.foodordering.service;

import com.example.foodordering.dto.request.LoginRequest;
import com.example.foodordering.dto.request.RegisterRequest;
import com.example.foodordering.dto.response.AuthResponse;
import com.example.foodordering.entity.Role;
import com.example.foodordering.entity.User;
import com.example.foodordering.exception.BadRequestException;
import com.example.foodordering.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setRole(Role.CUSTOMER);
        user.setEnabled(true);

        userRepository.save(user);

        return new AuthResponse(user.getId(), user.getFullName(), user.getEmail(), user.getRole().name());
    }

    // NOTE: This is a simplified login for a learning project.
    // It checks the password but does not issue a security token.
    // The frontend is trusted to remember the logged-in user's info.
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        if (!user.isEnabled()) {
            throw new BadRequestException("Account is disabled");
        }

        return new AuthResponse(user.getId(), user.getFullName(), user.getEmail(), user.getRole().name());
    }
}
