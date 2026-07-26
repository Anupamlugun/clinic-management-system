package com.clinic.cms.auth.controller.v1;

import com.clinic.cms.auth.dto.v1.request.PermissionCreateRequest;
import com.clinic.cms.auth.dto.v1.request.PermissionUpdateRequest;
import com.clinic.cms.auth.dto.v1.response.PermissionResponse;
import com.clinic.cms.auth.service.PermissionService;
import com.clinic.cms.common.dto.v1.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/permissions")
@RequiredArgsConstructor
@Tag(name = "Permission", description = "Permission management APIs")
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('CREATE_PERMISSION')")
    @Operation(summary = "Create permission")
    public ResponseEntity<ApiResponse<PermissionResponse>> create(
            @Valid @RequestBody PermissionCreateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Permission created successfully.",
                        permissionService.create(request)
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('UPDATE_PERMISSION')")
    @Operation(summary = "Update permission")
    public ResponseEntity<ApiResponse<PermissionResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody PermissionUpdateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Permission updated successfully.",
                        permissionService.update(id, request)
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('VIEW_PERMISSION')")
    @Operation(summary = "Get permission by ID")
    public ResponseEntity<ApiResponse<PermissionResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Permission retrieved successfully.",
                        permissionService.getById(id)
                )
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('VIEW_ALL_PERMISSIONS')")
    @Operation(summary = "Get all permissions")
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Permissions retrieved successfully.",
                        permissionService.getAll()
                )
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('DELETE_PERMISSION')")
    @Operation(summary = "Delete permission")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        permissionService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success("Permission deleted successfully.")
        );
    }
}