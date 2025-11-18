package com.ecommerce.project.utils;

import com.ecommerce.project.model.User;
import com.ecommerce.project.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthUtils {

    public UserRepository userRepository;

    public AuthUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String loggedInEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = userRepository.findByUsername(authentication.getName());
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User Not Found with username: %s", authentication.getName()));
        }
        return userOptional.get().getEmail();
    }

    public User loggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = userRepository.findByUsername(authentication.getName());
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User Not Found with username: %s", authentication.getName()));
        }
        return userOptional.get();
    }
}
