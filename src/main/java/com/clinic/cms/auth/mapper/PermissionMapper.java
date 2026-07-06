package com.clinic.cms.auth.mapper;

import com.clinic.cms.auth.dto.v1.request.PermissionCreateRequest;
import com.clinic.cms.auth.dto.v1.request.PermissionUpdateRequest;
import com.clinic.cms.auth.dto.v1.response.PermissionResponse;
import com.clinic.cms.auth.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PermissionMapper {

    Permission toEntity(PermissionCreateRequest request);

    PermissionResponse toResponse(Permission permission);

    void updateEntity(PermissionUpdateRequest request,
                      @MappingTarget Permission permission);
}