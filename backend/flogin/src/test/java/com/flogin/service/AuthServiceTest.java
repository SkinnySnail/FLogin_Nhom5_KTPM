package com.flogin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.flogin.dto.AuthResponse;

@DisplayName("Login Service Unit Tests")
class AuthServiceTest {

    private AuthService authService;

    @BeforeEach
    void setup() {
        authService = new AuthService();
    }

    @Test
    @DisplayName("TC_LOGIN_BE_01: Login thành công")
    void testLoginSuccess() {
        // Arrange
        String username = "testuser";
        String password = "Test123";
        
        // Act
        AuthResponse response = authService.authenticate(username, password);
        
        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("Đăng nhập thành công", response.getMessage());
        assertNotNull(response.getToken());
    }

    @Test
    @DisplayName("TC_LOGIN_BE_02: Login thất bại (username không tồn tại)")
    void testLoginFailure_UserNotFound() {
        // Arrange
        String username = "nonexistent";
        String password = "Test123";
        
        // Act
        AuthResponse response = authService.authenticate(username, password);
        
        // Assert
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals("Username không tồn tại", response.getMessage());
    }

    @Test
    @DisplayName("TC_LOGIN_BE_03: Login thất bại (password sai)")
    void testLoginFailure_WrongPassword() {
        // Arrange
        String username = "testuser";
        String password = "WrongPassword123";
        
        // Act
        AuthResponse response = authService.authenticate(username, password);
        
        // Assert
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals("Password không chính xác", response.getMessage());
    }
}
