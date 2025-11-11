package com.flogin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flogin.dto.AuthResponse;
import com.flogin.dto.LoginRequest;
import com.flogin.service.AuthService;

/**
 * Integration Tests cho AuthController
 * Test các API endpoints của Login với MockMvc
 */
@WebMvcTest(AuthController.class)
@DisplayName("Auth Controller Integration Tests")
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    @DisplayName("TC_LOGIN_INT_01: POST /api/auth/login - Đăng nhập thành công")
    void testLogin_Success() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest("testuser", "Test123");
        AuthResponse mockResponse = new AuthResponse(
            true, 
            "Đăng nhập thành công", 
            "token_testuser_123456"
        );

        when(authService.authenticate(any(String.class), any(String.class)))
            .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.message").value("Đăng nhập thành công"))
            .andExpect(jsonPath("$.token").value("token_testuser_123456"));
    }

    @Test
    @DisplayName("TC_LOGIN_INT_02: POST /api/auth/login - Username không tồn tại (401)")
    void testLogin_UserNotFound() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest("nonexistent", "Test123");
        AuthResponse mockResponse = new AuthResponse(false, "Username không tồn tại");

        when(authService.authenticate(any(String.class), any(String.class)))
            .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Username không tồn tại"));
    }

    @Test
    @DisplayName("TC_LOGIN_INT_03: POST /api/auth/login - Password sai (401)")
    void testLogin_WrongPassword() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest("testuser", "WrongPassword123");
        AuthResponse mockResponse = new AuthResponse(false, "Password không chính xác");

        when(authService.authenticate(any(String.class), any(String.class)))
            .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Password không chính xác"));
    }

    @Test
    @DisplayName("TC_LOGIN_INT_04: POST /api/auth/login - Request body rỗng (400)")
    void testLogin_EmptyRequestBody() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("TC_LOGIN_INT_05: POST /api/auth/login - Username rỗng")
    void testLogin_EmptyUsername() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest("", "Test123");
        AuthResponse mockResponse = new AuthResponse(false, "Username không được để trống");

        when(authService.authenticate(any(String.class), any(String.class)))
            .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Username không được để trống"));
    }

    @Test
    @DisplayName("TC_LOGIN_INT_06: POST /api/auth/login - Password rỗng")
    void testLogin_EmptyPassword() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest("testuser", "");
        AuthResponse mockResponse = new AuthResponse(false, "Password không được để trống");

        when(authService.authenticate(any(String.class), any(String.class)))
            .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Password không được để trống"));
    }

    @Test
    @DisplayName("TC_LOGIN_INT_07: GET /api/auth/health - Health check")
    void testHealthCheck() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/auth/health"))
            .andExpect(status().isOk())
            .andExpect(content().string("Auth API is running"));
    }

    @Test
    @DisplayName("TC_LOGIN_INT_08: POST /api/auth/login - Content-Type validation")
    void testLogin_ContentTypeValidation() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest("testuser", "Test123");

        // Act & Assert - Test với Content-Type không đúng
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.TEXT_PLAIN)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    @DisplayName("TC_LOGIN_INT_09: POST /api/auth/login - CORS headers")
    void testLogin_CORSHeaders() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest("testuser", "Test123");
        AuthResponse mockResponse = new AuthResponse(true, "Đăng nhập thành công", "token");

        when(authService.authenticate(any(String.class), any(String.class)))
            .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("Origin", "http://localhost:3000"))
            .andExpect(status().isOk())
            .andExpect(header().exists("Access-Control-Allow-Origin"));
    }

    @Test
    @DisplayName("TC_LOGIN_INT_10: POST /api/auth/login - Response structure validation")
    void testLogin_ResponseStructure() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest("testuser", "Test123");
        AuthResponse mockResponse = new AuthResponse(true, "Đăng nhập thành công", "token_abc");

        when(authService.authenticate(any(String.class), any(String.class)))
            .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").exists())
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.token").exists())
            .andExpect(jsonPath("$.success").isBoolean())
            .andExpect(jsonPath("$.message").isString())
            .andExpect(jsonPath("$.token").isString());
    }
}
