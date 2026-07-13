package com.clinic.cms.auth.security;

public interface CurrentUserService {

    CustomUserDetails getCurrentUser();

    Long getUserId();

    boolean hasRole(String role);

    boolean hasAuthority(String authority);
}