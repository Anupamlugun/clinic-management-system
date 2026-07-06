package com.clinic.cms.auth.service.impl;

import com.clinic.cms.auth.dto.v1.request.PermissionCreateRequest;
import com.clinic.cms.auth.dto.v1.request.PermissionUpdateRequest;
import com.clinic.cms.auth.dto.v1.response.PermissionResponse;
import com.clinic.cms.auth.entity.Permission;
import com.clinic.cms.auth.mapper.PermissionMapper;
import com.clinic.cms.auth.repository.PermissionRepository;
import com.clinic.cms.auth.service.PermissionService;
import com.clinic.cms.exception.custom.ResourceAlreadyExistsException;
import com.clinic.cms.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    public PermissionResponse create(PermissionCreateRequest request) {

        if (permissionRepository.existsByCode(request.code())) {
            throw new ResourceAlreadyExistsException(
                    "Permission already exists with code: " + request.code());
        }

        Permission permission = permissionMapper.toEntity(request);

        Permission savedPermission = permissionRepository.save(permission);

        return permissionMapper.toResponse(savedPermission);
    }

    @Override
    public PermissionResponse update(Long id, PermissionUpdateRequest request) {

        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Permission not found with id: " + id));

        if (!permission.getCode().equals(request.code())
                && permissionRepository.existsByCode(request.code())) {

            throw new ResourceAlreadyExistsException(
                    "Permission already exists with code: " + request.code());
        }

        permissionMapper.updateEntity(request, permission);

        Permission updatedPermission = permissionRepository.save(permission);

        return permissionMapper.toResponse(updatedPermission);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionResponse getById(Long id) {

        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Permission not found with id: " + id));

        return permissionMapper.toResponse(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionResponse> getAll() {

        return permissionRepository.findAll()
                .stream()
                .map(permissionMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Permission not found with id: " + id));

        permissionRepository.delete(permission);
    }
}