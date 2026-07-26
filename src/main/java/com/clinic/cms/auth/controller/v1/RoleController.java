package com.clinic.cms.auth.controller.v1;

import com.clinic.cms.auth.dto.v1.request.RoleCreateRequest;
import com.clinic.cms.auth.dto.v1.request.RoleUpdateRequest;
import com.clinic.cms.auth.dto.v1.response.RoleResponse;
import com.clinic.cms.auth.service.RoleService;
import com.clinic.cms.common.dto.v1.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Role", description = "Role management APIs")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('CREATE_ROLE')")
    @Operation(summary = "Create role")
    public ResponseEntity<ApiResponse<RoleResponse>> create(
            @Valid @RequestBody RoleCreateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Role created successfully.",
                        roleService.create(request)
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('UPDATE_ROLE')")
    @Operation(summary = "Update role")
    public ResponseEntity<ApiResponse<RoleResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody RoleUpdateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Role updated successfully.",
                        roleService.update(id, request)
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('VIEW_ROLE')")
    @Operation(summary = "Get role by ID")
    public ResponseEntity<ApiResponse<RoleResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Role retrieved successfully.",
                        roleService.getById(id)
                )
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('VIEW_ALL_ROLES')")
    @Operation(summary = "Get all roles")
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Roles retrieved successfully.",
                        roleService.getAll()
                )
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('DELETE_ROLE')")
    @Operation(summary = "Delete role")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        roleService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success("Role deleted successfully.")
        );
    }

    @PostMapping("/{roleId}/permissions")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('ASSIGN_PERMISSION')")
    @Operation(summary = "Assign permissions to role")
    public ResponseEntity<ApiResponse<Void>> assignPermissions(
            @PathVariable Long roleId,
            @RequestBody Set<Long> permissionIds) {

        roleService.assignPermissions(roleId, permissionIds);

        return ResponseEntity.ok(
                ApiResponse.success("Permissions assigned successfully.")
        );
    }

    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('ASSIGN_PERMISSION')")
    @Operation(summary = "Remove permission from role")
    public ResponseEntity<ApiResponse<Void>> removePermission(
            @PathVariable Long roleId,
            @PathVariable Long permissionId) {

        roleService.removePermission(roleId, permissionId);

        return ResponseEntity.ok(
                ApiResponse.success("Permission removed successfully.")
        );
    }
}