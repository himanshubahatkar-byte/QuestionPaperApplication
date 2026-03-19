package com.project.questionpapers.auth;

import com.project.questionpapers.dto.ForgotPasswordRequest;
import com.project.questionpapers.dto.LoginRequest;
import com.project.questionpapers.dto.LoginResponse;
import com.project.questionpapers.dto.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        authenticationService.registerStudent(request);

        return ResponseEntity.ok("Student registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        LoginResponse response = authenticationService.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {

        String response = authenticationService.resetPassword(request);

        return ResponseEntity.ok(response);
    }
}
