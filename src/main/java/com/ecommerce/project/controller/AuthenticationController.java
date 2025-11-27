package com.ecommerce.project.controller;

import com.ecommerce.project.security.request.SignupRequest;
import com.ecommerce.project.security.request.UserInfoRequest;
import com.ecommerce.project.security.response.UserInfoResponse;
import com.ecommerce.project.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Tag(name = "Authentication APIs", description = "APIs to manage authentication process")
    @Operation(summary = "Authenticate user", description = "Authenticate and log the current user in")
    @PostMapping("/signin")
    public ResponseEntity<UserInfoResponse> authenticateUser(@RequestBody UserInfoRequest userInfoRequest) {
        UserInfoResponse userInfoResponse = authenticationService.authenticateUser(userInfoRequest);
        String jwtCookieStr = userInfoResponse.getJwtCookie().toString();
        // userInfoResponse.setJwtCookie(null);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.SET_COOKIE, jwtCookieStr);
        return new ResponseEntity<>(userInfoResponse, responseHeaders, HttpStatus.OK);
    }

    @Tag(name = "Authentication APIs", description = "APIs to manage authentication process")
    @Operation(summary = "Register user", description = "Register the log the current user in")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        return new ResponseEntity<>(authenticationService.registerUser(signupRequest), HttpStatus.OK);
    }

    @Tag(name = "Authentication APIs", description = "APIs to manage authentication process")
    @Operation(summary = "Get the current username", description = "Get the username of the current authenticated user")
    @GetMapping("/username")
    public ResponseEntity<String> currentUsername(Authentication authentication) {
        return new ResponseEntity<>(authenticationService.getUsername(authentication), HttpStatus.OK);
    }

    @Tag(name = "Authentication APIs", description = "APIs to manage authentication process")
    @Operation(summary = "Sign out user", description = "Sign the current authenticated user out")
    @PostMapping("/signout")
    public ResponseEntity<String> signoutUser() {
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set(HttpHeaders.SET_COOKIE, authenticationService.signoutUser().toString());
        return new ResponseEntity<>("Sign out successfully", responseHeader, HttpStatus.OK);
    }
}
