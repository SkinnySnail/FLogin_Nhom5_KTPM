package com.flogin.service;

import org.springframework.stereotype.Service;

import com.flogin.dto.AuthResponse;

@Service
public class AuthService {
    
    // Hardcoded user cho test (sau này sẽ dùng database)
    private static final String VALID_USERNAME = "testuser";
    private static final String VALID_PASSWORD = "Test123";

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
        if (!username.equals(VALID_USERNAME)) {
            return new AuthResponse(false, "Username không tồn tại");
        }
        
        // Check if password is correct
        if (!password.equals(VALID_PASSWORD)) {
            return new AuthResponse(false, "Password không chính xác");
        }
        
        // Generate token (simple implementation)
        String token = generateToken(username);
        
        return new AuthResponse(true, "Đăng nhập thành công", token);
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
