package com.hadygust.ecommerce.service;

import com.hadygust.ecommerce.dto.request.LoginRequest;
import com.hadygust.ecommerce.dto.request.RegisterRequest;
import com.hadygust.ecommerce.dto.response.UserResponse;
import com.hadygust.ecommerce.entity.User;
import com.hadygust.ecommerce.mapper.UserMapper;
import com.hadygust.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;
    private final UserMapper mapper;

    public UserResponse register(RegisterRequest req){
        User saved = repo.save(mapper.register(req));
        return mapper.toResponse(saved);
    }

    public String login(LoginRequest req){
        Optional<User> found = repo.findUserByEmailAndPassword(req.email(), req.password());
        UserResponse loggedIn = mapper.toResponse(found.orElseThrow(WrongThreadException::new));



        // Generate JWT

        return loggedIn.email();
    }


}
