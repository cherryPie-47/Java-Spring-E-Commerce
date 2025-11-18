package com.ecommerce.project.controller;

import com.ecommerce.project.security.request.SignupRequest;
import com.ecommerce.project.security.request.UserInfoRequest;
import com.ecommerce.project.security.response.UserInfoResponse;
import com.ecommerce.project.service.AuthenticationService;
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

    @PostMapping("/signin")
    public ResponseEntity<UserInfoResponse> authenticateUser(@RequestBody UserInfoRequest userInfoRequest) {
        UserInfoResponse userInfoResponse = authenticationService.authenticateUser(userInfoRequest);
        String jwtCookieStr = userInfoResponse.getJwtCookie().toString();
        userInfoResponse.setJwtCookie(null);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.SET_COOKIE, jwtCookieStr);
        return new ResponseEntity<>(userInfoResponse, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        return new ResponseEntity<>(authenticationService.registerUser(signupRequest), HttpStatus.OK);
    }

    @GetMapping("/username")
    public ResponseEntity<String> currentUsername(Authentication authentication) {
        return new ResponseEntity<>(authenticationService.getUsername(authentication), HttpStatus.OK);
    }

    @PostMapping("/signout")
    public ResponseEntity<String> signoutUser() {
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set(HttpHeaders.SET_COOKIE, authenticationService.signoutUser().toString());
        return new ResponseEntity<>("Sign out successfully", responseHeader, HttpStatus.OK);
    }
}
