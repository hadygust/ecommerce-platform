package com.hadygust.ecommerce.security;

import com.hadygust.ecommerce.entity.User;
import com.hadygust.ecommerce.exception.UserEmailNotFoundExcecption;
import com.hadygust.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findUserByEmail(email).orElseThrow(() -> new UserEmailNotFoundExcecption(email));
        return new CustomUserDetails(user);
    }
}
