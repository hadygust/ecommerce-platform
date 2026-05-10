package com.hadygust.ecommerce.mapper;

import com.hadygust.ecommerce.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailMapper {

    public CustomUserDetails toCustomUserDetail(UserDetails userDetails){
        return (CustomUserDetails) userDetails;
    }

}
