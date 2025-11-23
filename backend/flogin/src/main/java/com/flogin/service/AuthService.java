package com.flogin.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.flogin.dto.AuthResponse;
import com.flogin.entity.User;
import com.flogin.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Authenticate user with username and password
     * @param username username to authenticate
     * @param password password to authenticate
     * @return AuthResponse with success status and token
     */
    public AuthResponse authenticate(String username, String password) {
        // Validate input
        if (username == null || username.isEmpty()) {
            return new AuthResponse(false, "Username không được để trống");
        }
        if (password == null || password.isEmpty()) {
            return new AuthResponse(false, "Password không được để trống");
        }
        // Check if user exists
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return new AuthResponse(false, "Username không tồn tại");
        }
        User user = userOpt.get();
        // Check if password is correct
        if (!user.getPassword().equals(password)) {
            return new AuthResponse(false, "Password không chính xác");
        }
        // Generate token (simple implementation)
        String token = generateToken(username);
        Long expiresIn = 86400000L; // 24 hours in milliseconds
        return new AuthResponse(true, "Đăng nhập thành công", token, username, expiresIn);
    }

    /**
     * Register a new user
     * @param username username for new user
     * @param password password for new user
     * @return AuthResponse with success status
     */
    public AuthResponse register(String username, String password) {
        // Validate input
        if (username == null || username.isEmpty()) {
            return new AuthResponse(false, "Username không được để trống");
        }
        if (password == null || password.isEmpty()) {
            return new AuthResponse(false, "Password không được để trống");
        }
        
        // Check if username already exists
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            return new AuthResponse(false, "Username đã tồn tại");
        }
        
        // Create new user
        User newUser = new User(username, password);
        userRepository.save(newUser);
        
        return new AuthResponse(true, "Đăng ký thành công");
    }

    /**
     * Generate JWT token (simplified version)
     * @param username username to encode in token
     * @return token string
     */
    private String generateToken(String username) {
        // Simple token generation (in production, use JWT)
        return "token_" + username + "_" + System.currentTimeMillis();
    }
}
