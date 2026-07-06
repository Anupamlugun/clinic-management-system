package com.clinic.cms.auth.service.impl;

import com.clinic.cms.auth.dto.v1.request.RoleCreateRequest;
import com.clinic.cms.auth.dto.v1.request.RoleUpdateRequest;
import com.clinic.cms.auth.dto.v1.response.RoleResponse;
import com.clinic.cms.auth.entity.Permission;
import com.clinic.cms.auth.entity.Role;
import com.clinic.cms.auth.mapper.RoleMapper;
import com.clinic.cms.auth.repository.PermissionRepository;
import com.clinic.cms.auth.repository.RoleRepository;
import com.clinic.cms.auth.service.RoleService;
import com.clinic.cms.exception.custom.ResourceAlreadyExistsException;
import com.clinic.cms.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse create(RoleCreateRequest request) {

        if (roleRepository.existsByCode(request.code())) {
            throw new ResourceAlreadyExistsException(
                    "Role already exists with code: " + request.code());
        }

        Role role = roleMapper.toEntity(request);

        if (request.permissionIds() != null && !request.permissionIds().isEmpty()) {
            Set<Permission> permissions = new HashSet<>(
                    permissionRepository.findByIdIn(request.permissionIds())
            );

            if (permissions.size() != request.permissionIds().size()) {
                throw new ResourceNotFoundException("One or more permissions not found.");
            }

            role.setPermissions(permissions);
        }

        Role savedRole = roleRepository.save(role);

        return roleMapper.toResponse(savedRole);
    }

    @Override
    public RoleResponse update(Long id, RoleUpdateRequest request) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with id: " + id));

        if (!role.getCode().equals(request.code())
                && roleRepository.existsByCode(request.code())) {

            throw new ResourceAlreadyExistsException(
                    "Role already exists with code: " + request.code());
        }

        roleMapper.updateEntity(request, role);

        if (request.permissionIds() != null) {

            Set<Permission> permissions = new HashSet<>(
                    permissionRepository.findByIdIn(request.permissionIds())
            );

            if (permissions.size() != request.permissionIds().size()) {
                throw new ResourceNotFoundException("One or more permissions not found.");
            }

            role.setPermissions(permissions);
        }

        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getById(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with id: " + id));

        return roleMapper.toResponse(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getAll() {

        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with id: " + id));

        roleRepository.delete(role);
    }

    @Override
    public void assignPermissions(Long roleId, Set<Long> permissionIds) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with id: " + roleId));

        Set<Permission> permissions = new HashSet<>(
                permissionRepository.findByIdIn(permissionIds)
        );

        if (permissions.size() != permissionIds.size()) {
            throw new ResourceNotFoundException("One or more permissions not found.");
        }

        role.getPermissions().addAll(permissions);

        roleRepository.save(role);
    }

    @Override
    public void removePermission(Long roleId, Long permissionId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with id: " + roleId));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Permission not found with id: " + permissionId));

        role.getPermissions().remove(permission);

        roleRepository.save(role);
    }
}