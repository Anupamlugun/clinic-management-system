package com.clinic.cms.auth.service;

import com.clinic.cms.auth.dto.v1.request.RoleCreateRequest;
import com.clinic.cms.auth.dto.v1.request.RoleUpdateRequest;
import com.clinic.cms.auth.dto.v1.response.RoleResponse;

import java.util.List;
import java.util.Set;

public interface RoleService {

    RoleResponse create(RoleCreateRequest request);

    RoleResponse update(Long id, RoleUpdateRequest request);

    RoleResponse getById(Long id);

    List<RoleResponse> getAll();

    void delete(Long id);

    void assignPermissions(Long roleId, Set<Long> permissionIds);

    void removePermission(Long roleId, Long permissionId);
}