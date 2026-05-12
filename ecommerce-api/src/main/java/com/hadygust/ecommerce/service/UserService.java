package com.hadygust.ecommerce.service;

import com.hadygust.ecommerce.dto.request.UserUpdateRequest;
import com.hadygust.ecommerce.dto.response.UserResponse;
import com.hadygust.ecommerce.entity.User;
import com.hadygust.ecommerce.helper.UserUtils;
import com.hadygust.ecommerce.mapper.UserMapper;
import com.hadygust.ecommerce.repository.UserRepository;
import com.hadygust.ecommerce.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;
    private final UserMapper mapper;
    private final UserUtils userUtils;

    public UserResponse getUser(){
        User user = userUtils.getUser();
        return mapper.toResponse(user);
    }


    public UserResponse updateUser(UserUpdateRequest req) {
        User user = userUtils.getUser();

        user.setEmail(req.email());
        user.setName(req.name());

        return mapper.toResponse(repo.save(user));
    }
}
