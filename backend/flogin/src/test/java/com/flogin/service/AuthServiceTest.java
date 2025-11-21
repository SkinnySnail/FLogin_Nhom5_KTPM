package com.flogin.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import com.flogin.dto.AuthResponse;
import com.flogin.entity.User;
import com.flogin.repository.UserRepository;
import com.flogin.service.AuthService;

@DisplayName("Login Service Unit Tests")
class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("TC1: Login thành công với credentials hợp lệ")
    void testLoginSuccess() {
        String username = "testuser";
        String password = "Test123";
        User user = new User(username, password);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        AuthResponse response = authService.authenticate(username, password);

        assertTrue(response.isSuccess());
        assertEquals("Đăng nhập thành công", response.getMessage());
        assertNotNull(response.getToken());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("TC2: Login thất bại với username không tồn tại")
    void testLoginWithNonExistentUsername() {
        String username = "wronguser";
        String password = "Test123";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        AuthResponse response = authService.authenticate(username, password);

        assertFalse(response.isSuccess());
        assertEquals("Username không tồn tại", response.getMessage());
        assertNull(response.getToken());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("TC3: Login thất bại với password sai")
    void testLoginWithWrongPassword() {
        String username = "testuser";
        String password = "WrongPass";
        User user = new User(username, "Test123");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        AuthResponse response = authService.authenticate(username, password);

        assertFalse(response.isSuccess());
        assertEquals("Password không chính xác", response.getMessage());
        assertNull(response.getToken());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("TC4: Login thất bại với validation lỗi (username rỗng)")
    void testLoginWithEmptyUsername() {
        String username = "";
        String password = "Test123";
        AuthResponse response = authService.authenticate(username, password);
        assertFalse(response.isSuccess());
        assertEquals("Username không được để trống", response.getMessage());
        assertNull(response.getToken());
        verify(userRepository, never()).findByUsername(anyString());
    }

    @Test
    @DisplayName("TC5: Login thất bại với validation lỗi (password rỗng)")
    void testLoginWithEmptyPassword() {
        String username = "testuser";
        String password = "";
        AuthResponse response = authService.authenticate(username, password);
        assertFalse(response.isSuccess());
        assertEquals("Password không được để trống", response.getMessage());
        assertNull(response.getToken());
        verify(userRepository, never()).findByUsername(anyString());
    }
}
