package com.hadygust.ecommerce.mapper;

import com.hadygust.ecommerce.dto.request.RegisterRequest;
import com.hadygust.ecommerce.dto.response.UserResponse;
import com.hadygust.ecommerce.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User register(RegisterRequest req){
        User user = new User();
        user.setName(req.name());
        user.setEmail(req.email());
        user.setPassword(req.password());

        return user;
    }

    public UserResponse toResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail());
    }
}
