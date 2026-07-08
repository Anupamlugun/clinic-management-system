package com.clinic.cms.auth.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface JwtService {

    String generateAccessToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    String extractUsername(String token);

    boolean isTokenValid(String token);

    long getAccessTokenExpiration();

    long getRefreshTokenExpiration();

    Collection<? extends GrantedAuthority> extractAuthorities(String token);
}