package com.hadygust.ecommerce.helper;

import com.hadygust.ecommerce.entity.User;
import com.hadygust.ecommerce.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

    public User getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return ((CustomUserDetails)auth.getPrincipal()).getUser();
    }
}
