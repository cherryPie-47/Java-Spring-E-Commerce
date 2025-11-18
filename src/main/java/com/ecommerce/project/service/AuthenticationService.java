package com.ecommerce.project.service;

import com.ecommerce.project.security.request.SignupRequest;
import com.ecommerce.project.security.request.UserInfoRequest;
import com.ecommerce.project.security.response.MessageResponse;
import com.ecommerce.project.security.response.UserInfoResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    public UserInfoResponse authenticateUser(UserInfoRequest userInfoRequest);
    public MessageResponse registerUser(SignupRequest signupRequest);
    public String getUsername(Authentication authentication);
    public ResponseCookie signoutUser();
}
