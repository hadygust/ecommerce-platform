package com.hadygust.ecommerce.service;

import com.hadygust.ecommerce.dto.request.LoginRequest;
import com.hadygust.ecommerce.dto.request.RegisterRequest;
import com.hadygust.ecommerce.dto.request.UserUpdateRequest;
import com.hadygust.ecommerce.dto.response.UserResponse;
import com.hadygust.ecommerce.entity.User;
import com.hadygust.ecommerce.mapper.UserMapper;
import com.hadygust.ecommerce.repository.UserRepository;
import com.hadygust.ecommerce.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;
    private final UserMapper mapper;

    public UserResponse getUser(CustomUserDetails userDetails){
        User user = userDetails.getUser();
        return mapper.toResponse(user);
    }


    public UserResponse updateUser(CustomUserDetails userDetails, UserUpdateRequest req) {
        User user = userDetails.getUser();

        user.setEmail(req.email());
        user.setName(req.name());

        return mapper.toResponse(repo.save(user));
    }
}
