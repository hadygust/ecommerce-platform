package com.hadygust.ecommerce.controller;

import com.hadygust.ecommerce.dto.request.UserUpdateRequest;
import com.hadygust.ecommerce.dto.response.UserResponse;
import com.hadygust.ecommerce.entity.User;
import com.hadygust.ecommerce.security.CustomUserDetails;
import com.hadygust.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ResponseEntity.ok(service.getUser(userDetails));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMe(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                 @RequestBody UserUpdateRequest req){
        return ResponseEntity.ok(service.updateUser(userDetails, req));
    }

}
