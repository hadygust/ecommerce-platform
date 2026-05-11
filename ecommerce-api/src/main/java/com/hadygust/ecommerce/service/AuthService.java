package com.hadygust.ecommerce.service;

import com.hadygust.ecommerce.dto.request.LoginRequest;
import com.hadygust.ecommerce.dto.request.RegisterRequest;
import com.hadygust.ecommerce.dto.response.UserResponse;
import com.hadygust.ecommerce.entity.User;
import com.hadygust.ecommerce.exception.WrongCredentialException;
import com.hadygust.ecommerce.mapper.UserMapper;
import com.hadygust.ecommerce.repository.UserRepository;
import com.hadygust.ecommerce.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final UserMapper mapper;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;


    public String login(LoginRequest req){
        User user = repo.findUserByEmail(req.email()).orElseThrow(() -> new WrongCredentialException());

        if(!encoder.matches(req.password(), user.getPassword())){
            throw new WrongCredentialException();
        }

        return jwtService.generateToken(user);
    }

    public UserResponse register(RegisterRequest req){

        User user = mapper.register(req);

        user.setPassword(encoder.encode(user.getPassword()));

        User saved = repo.save(user);

        return mapper.toResponse(saved);
    }


}
