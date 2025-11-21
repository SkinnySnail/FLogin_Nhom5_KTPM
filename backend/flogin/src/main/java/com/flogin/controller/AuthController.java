package com.flogin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flogin.dto.AuthResponse;
import com.flogin.dto.LoginRequest;
import com.flogin.service.AuthService;

/**
 * REST Controller cho Authentication endpoints
 * Xử lý các API liên quan đến đăng nhập
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * POST /api/auth/login - Đăng nhập
     * 
     * @param request LoginRequest với username và password
     * @return AuthResponse với token nếu thành công
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Validate request body không null
        if (request == null) {
            return ResponseEntity
                .badRequest()
                .body(new AuthResponse(false, "Request body không được rỗng"));
        }

        // Gọi service để authenticate
        AuthResponse response = authService.authenticate(request.getUsername(), request.getPassword());

        // Trả về response với status code phù hợp
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
        }
    }

    /**
     * GET /api/auth/health - Health check endpoint
     * 
     * @return Success message
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth API is running");
    }
}
