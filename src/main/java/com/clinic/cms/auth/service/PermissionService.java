package com.clinic.cms.auth.service;

import com.clinic.cms.auth.dto.v1.request.PermissionCreateRequest;
import com.clinic.cms.auth.dto.v1.request.PermissionUpdateRequest;
import com.clinic.cms.auth.dto.v1.response.PermissionResponse;

import java.util.List;

public interface PermissionService {

    PermissionResponse create(PermissionCreateRequest request);

    PermissionResponse update(Long id, PermissionUpdateRequest request);

    PermissionResponse getById(Long id);

    List<PermissionResponse> getAll();

    void delete(Long id);
}