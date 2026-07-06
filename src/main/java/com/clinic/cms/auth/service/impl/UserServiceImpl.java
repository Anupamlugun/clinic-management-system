package com.clinic.cms.auth.service.impl;

import com.clinic.cms.auth.dto.v1.request.UserCreateRequest;
import com.clinic.cms.auth.dto.v1.request.UserUpdateRequest;
import com.clinic.cms.auth.dto.v1.response.UserResponse;
import com.clinic.cms.auth.entity.Role;
import com.clinic.cms.auth.entity.User;
import com.clinic.cms.auth.mapper.UserMapper;
import com.clinic.cms.auth.repository.RoleRepository;
import com.clinic.cms.auth.repository.UserRepository;
import com.clinic.cms.auth.service.UserService;
import com.clinic.cms.exception.custom.ResourceAlreadyExistsException;
import com.clinic.cms.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserCreateRequest request) {

        if (userRepository.existsByUsername(request.username())) {
            throw new ResourceAlreadyExistsException(
                    "Username already exists: " + request.username());
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistsException(
                    "Email already exists: " + request.email());
        }

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.password()));

        return userMapper.toResponse(createUser(user));
    }

    @Override
    public User createUser(User request){

        Set<Role> roles = new HashSet<>(roleRepository.findByIdIn(request.getRoleIds()));

        if (roles.size() != request.getRoleIds().size()) {
            throw new ResourceNotFoundException("One or more roles not found.");
        }

        request.setRoles(roles);

        return userRepository.save(request);
    }

    @Override
    public UserResponse update(Long id, UserUpdateRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        if (!user.getUsername().equals(request.username())
                && userRepository.existsByUsername(request.username())) {

            throw new ResourceAlreadyExistsException(
                    "Username already exists: " + request.username());
        }

        if (!user.getEmail().equals(request.email())
                && userRepository.existsByEmail(request.email())) {

            throw new ResourceAlreadyExistsException(
                    "Email already exists: " + request.email());
        }

        userMapper.updateEntity(request, user);

        if (request.roleIds() != null) {

            Set<Role> roles = new HashSet<>(roleRepository.findByIdIn(request.roleIds()));

            if (roles.size() != request.roleIds().size()) {
                throw new ResourceNotFoundException("One or more roles not found.");
            }

            user.setRoles(roles);
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }

    @Override
    public void activate(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        user.setActive(true);

        userRepository.save(user);
    }

    @Override
    public void deactivate(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        user.setActive(false);

        userRepository.save(user);
    }

    @Override
    public void assignRoles(Long userId, Set<Long> roleIds) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId));

        Set<Role> roles = new HashSet<>(roleRepository.findByIdIn(roleIds));

        if (roles.size() != roleIds.size()) {
            throw new ResourceNotFoundException("One or more roles not found.");
        }

        user.getRoles().addAll(roles);

        userRepository.save(user);
    }

    @Override
    public void removeRole(Long userId, Long roleId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with id: " + roleId));

        user.getRoles().remove(role);

        userRepository.save(user);
    }
}