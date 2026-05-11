package com.hadygust.ecommerce.controller;

import com.hadygust.ecommerce.dto.request.LoginRequest;
import com.hadygust.ecommerce.dto.request.RegisterRequest;
import com.hadygust.ecommerce.dto.response.UserResponse;
import com.hadygust.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest req){
        return ResponseEntity.ok(service.login(req));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest req){
        return ResponseEntity.ok(service.register(req));
    }
}
