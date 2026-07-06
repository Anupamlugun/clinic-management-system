package com.clinic.cms.auth.service;

import com.clinic.cms.auth.dto.v1.request.UserCreateRequest;
import com.clinic.cms.auth.dto.v1.request.UserUpdateRequest;
import com.clinic.cms.auth.dto.v1.response.UserResponse;
import com.clinic.cms.auth.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    UserResponse create(UserCreateRequest request);

    User createUser(User request);

    UserResponse update(Long id, UserUpdateRequest request);

    UserResponse getById(Long id);

    List<UserResponse> getAll();

    void delete(Long id);

    void activate(Long id);

    void deactivate(Long id);

    void assignRoles(Long userId, Set<Long> roleIds);

    void removeRole(Long userId, Long roleId);
}