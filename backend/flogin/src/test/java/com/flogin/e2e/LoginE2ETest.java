package com.flogin.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flogin.dto.AuthResponse;
import com.flogin.dto.LoginRequest;
import com.flogin.entity.User;
import com.flogin.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        userRepository.save(new User("testuser", "Test123"));
    }

    @Test
    void testLoginSuccess() {
        String url = "http://localhost:" + port + "/api/auth/login";
        LoginRequest request = new LoginRequest("testuser", "Test123");
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(url, request, AuthResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
    }

    @Test
    void testLoginFail() {
        String url = "http://localhost:" + port + "/api/auth/login";
        LoginRequest request = new LoginRequest("wronguser", "wrongpass");
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(url, request, AuthResponse.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
    }
}
