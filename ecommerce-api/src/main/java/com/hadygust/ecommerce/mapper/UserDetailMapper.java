package com.hadygust.ecommerce.mapper;

import com.hadygust.ecommerce.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailMapper {

    public CustomUserDetails toCustomUserDetail(UserDetails userDetails){
        return (CustomUserDetails) userDetails;
    }

}
