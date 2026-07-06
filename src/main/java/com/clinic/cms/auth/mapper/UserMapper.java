package com.clinic.cms.auth.mapper;

import com.clinic.cms.auth.dto.v1.request.UserCreateRequest;
import com.clinic.cms.auth.dto.v1.request.UserUpdateRequest;
import com.clinic.cms.auth.dto.v1.response.UserResponse;
import com.clinic.cms.auth.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        uses = RoleMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(UserCreateRequest request);

    UserResponse toResponse(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntity(UserUpdateRequest request,
                      @MappingTarget User user);
}