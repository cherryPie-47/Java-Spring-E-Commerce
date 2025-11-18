package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.AppRole;
import com.ecommerce.project.model.Role;
import com.ecommerce.project.model.User;
import com.ecommerce.project.repositories.RoleRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.security.request.SignupRequest;
import com.ecommerce.project.security.request.UserInfoRequest;
import com.ecommerce.project.security.response.MessageResponse;
import com.ecommerce.project.security.response.UserInfoResponse;
import com.ecommerce.project.security.jwt.JwtUtils;
import com.ecommerce.project.services.UserDetailsImpl;
import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     JwtUtils jwtUtils,
                                     UserRepository userRepository,
                                     RoleRepository roleRepository,
                                     PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserInfoResponse authenticateUser(UserInfoRequest userInfoRequest) {
        Authentication authentication = null;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userInfoRequest.getUsername(),
                            userInfoRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Bad Credentials");
            body.put("status", false);

        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority()).toList();

        return new UserInfoResponse(userDetails.getId(),
                jwtCookie,
                userDetails.getUsername(),
                roles);
    }

    @Override
    public MessageResponse registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return new MessageResponse("Error: Username has already been taken");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return new MessageResponse("Error: Email has already been taken");
        }

        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword())
        );

        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        // Default role for new user
        if (strRoles == null) {
            Optional<Role> userRoleOptional = roleRepository.findByRoleName(AppRole.ROLE_USER);
            if (userRoleOptional.isEmpty()) {
                throw new RuntimeException("Role is not found");
            }
            roles.add(userRoleOptional.get());
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Optional<Role> adminRoleOptional = roleRepository.findByRoleName(AppRole.ROLE_ADMIN);
                        if (adminRoleOptional.isEmpty()) {
                            throw new RuntimeException("Role is not found");
                        }
                        roles.add(adminRoleOptional.get());
                        break;
                    case "seller":
                        Optional<Role> sellerRoleOptional = roleRepository.findByRoleName(AppRole.ROLE_SELLER);
                        if (sellerRoleOptional.isEmpty()) {
                            throw new RuntimeException("Role is not found");
                        }
                        roles.add(sellerRoleOptional.get());
                        break;
                    default:
                        Optional<Role> userRoleOptional = roleRepository.findByRoleName(AppRole.ROLE_USER);
                        if (userRoleOptional.isEmpty()) {
                            throw new RuntimeException("Role is not found");
                        }
                        roles.add(userRoleOptional.get());
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return new MessageResponse("User registered successfully");
    }

    @Override
    public String getUsername(Authentication authentication) {
        if (authentication != null) {
            return authentication.getName();
        }
        else {
            return "";
        }
    }

    @Override
    public ResponseCookie signoutUser() {
        ResponseCookie cleanCookie = jwtUtils.getCleanCookie();

        return cleanCookie;
    }
}
