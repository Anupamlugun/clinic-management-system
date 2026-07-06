package com.clinic.cms.auth.service;

import com.clinic.cms.auth.dto.v1.request.LoginRequest;
import com.clinic.cms.auth.dto.v1.response.LoginResponse;
import com.clinic.cms.auth.dto.v1.response.UserResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    LoginResponse refreshToken(String refreshToken);

    void logout(String token);

    UserResponse me();
}