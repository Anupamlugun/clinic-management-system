package com.clinic.cms.auth.mapper;

import com.clinic.cms.auth.dto.v1.request.RoleCreateRequest;
import com.clinic.cms.auth.dto.v1.request.RoleUpdateRequest;
import com.clinic.cms.auth.dto.v1.response.RoleResponse;
import com.clinic.cms.auth.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        uses = PermissionMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "users", ignore = true)
    Role toEntity(RoleCreateRequest request);

    RoleResponse toResponse(Role role);

    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "users", ignore = true)
    void updateEntity(RoleUpdateRequest request,
                      @MappingTarget Role role);
}