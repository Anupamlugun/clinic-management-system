package com.clinic.cms.auth.service.impl;

import com.clinic.cms.auth.dto.v1.request.LoginRequest;
import com.clinic.cms.auth.dto.v1.response.LoginResponse;
import com.clinic.cms.auth.dto.v1.response.UserResponse;
import com.clinic.cms.auth.entity.User;
import com.clinic.cms.auth.mapper.UserMapper;
import com.clinic.cms.auth.repository.UserRepository;
import com.clinic.cms.auth.security.JwtService;
import com.clinic.cms.auth.service.AuthService;
import com.clinic.cms.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public LoginResponse login(LoginRequest request) {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.username(),
                                    request.password()
                            )
                    );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            User user = userRepository.findWithRolesByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found."));

            String accessToken = jwtService.generateAccessToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);

            return new LoginResponse(
                    accessToken,
                    refreshToken,
                    "Bearer",
                    jwtService.getAccessTokenExpiration(),
                    userMapper.toResponse(user)
            );

        } catch (BadCredentialsException | UsernameNotFoundException ex) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {

        String username = jwtService.extractUsername(refreshToken);

        User user = userRepository.findWithRolesByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        UserDetails userDetails = new com.clinic.cms.auth.security.CustomUserDetails(user);

        if (!jwtService.isTokenValid(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token.");
        }

        String newAccessToken = jwtService.generateAccessToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);

        return new LoginResponse(
                newAccessToken,
                newRefreshToken,
                "Bearer",
                jwtService.getAccessTokenExpiration(),
                userMapper.toResponse(user)
        );
    }

    @Override
    public void logout(String token) {
        // Stateless JWT: client simply discards the token.
        // If using TokenRepository, mark the token as revoked here.
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse me() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {

            throw new BadCredentialsException("User is not authenticated.");
        }

        String username = authentication.getName();

        User user = userRepository.findWithRolesByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        return userMapper.toResponse(user);
    }
}