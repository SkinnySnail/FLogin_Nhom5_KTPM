package com.flogin.controller;

import com.flogin.dto.AuthResponse;
import com.flogin.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(com.flogin.controller.AuthController.class)
class AuthControllerMockTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    @DisplayName("Mock: Controller với mocked service success")
    void testLoginWithMockedService() throws Exception {
        AuthResponse mockResponse = new AuthResponse(true, "Success", "mock-token");
        when(authService.authenticate(any(String.class), any(String.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"test\",\"password\":\"Pass123\"}"))
                .andExpect(status().isOk());

        verify(authService, times(1)).authenticate(any(String.class), any(String.class));
    }
}
