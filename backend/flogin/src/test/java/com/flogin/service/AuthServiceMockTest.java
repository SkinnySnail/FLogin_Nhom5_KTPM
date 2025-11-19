package com.flogin.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.flogin.dto.AuthResponse;
import com.flogin.entity.User;
import com.flogin.repository.UserRepository;

@DisplayName("Auth Service Mock Unit Tests")
class AuthServiceMockTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("TC_AUTH_MOCK_01: Login thành công")
    void testLoginSuccess() {
        // Arrange
        String username = "testuser";
        String password = "Test123";
        User user = new User(username, password);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        AuthResponse response = authService.authenticate(username, password);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("Đăng nhập thành công", response.getMessage());
        assertNotNull(response.getToken());
    }

    @Test
    @DisplayName("TC_AUTH_MOCK_02: Login thất bại (username không tồn tại)")
    void testLoginFailure_UserNotFound() {
        // Arrange
        String username = "nonexistent";
        String password = "Test123";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        AuthResponse response = authService.authenticate(username, password);

        // Assert
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals("Username không tồn tại", response.getMessage());
    }

    @Test
    @DisplayName("TC_AUTH_MOCK_03: Login thất bại (password sai)")
    void testLoginFailure_WrongPassword() {
        // Arrange
        String username = "testuser";
        String password = "WrongPassword123";
        User user = new User(username, "Test123");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        AuthResponse response = authService.authenticate(username, password);

        // Assert
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals("Password không chính xác", response.getMessage());
    }
}
