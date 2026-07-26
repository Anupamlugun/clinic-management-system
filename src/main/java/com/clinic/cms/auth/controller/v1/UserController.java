package com.clinic.cms.auth.controller.v1;

import com.clinic.cms.auth.dto.v1.request.UserCreateRequest;
import com.clinic.cms.auth.dto.v1.request.UserUpdateRequest;
import com.clinic.cms.auth.dto.v1.response.UserResponse;
import com.clinic.cms.auth.service.UserService;
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
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management APIs")
public class UserController {

    private final UserService userService;


    @PostMapping
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('CREATE_USER')")
    @Operation(summary = "Create user")
    public ResponseEntity<ApiResponse<UserResponse>> create(
            @Valid @RequestBody UserCreateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "User created successfully.",
                        userService.create(request)
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('UPDATE_USER')")
    @Operation(summary = "Update user")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "User updated successfully.",
                        userService.update(id, request)
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('VIEW_USER')")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<ApiResponse<UserResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "User retrieved successfully.",
                        userService.getById(id)
                )
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('VIEW_ALL_USERS')")
    @Operation(summary = "Get all users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Users retrieved successfully.",
                        userService.getAll()
                )
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('DELETE_USER')")
    @Operation(summary = "Delete user")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        userService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success("User deleted successfully.")
        );
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('ACTIVATE_USER')")
    @Operation(summary = "Activate user")
    public ResponseEntity<ApiResponse<Void>> activate(
            @PathVariable Long id) {

        userService.activate(id);

        return ResponseEntity.ok(
                ApiResponse.success("User activated successfully.")
        );
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('DEACTIVATE_USER')")
    @Operation(summary = "Deactivate user")
    public ResponseEntity<ApiResponse<Void>> deactivate(
            @PathVariable Long id) {

        userService.deactivate(id);

        return ResponseEntity.ok(
                ApiResponse.success("User deactivated successfully.")
        );
    }

    @PostMapping("/{userId}/roles")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('ASSIGN_ROLE')")
    @Operation(summary = "Assign roles to user")
    public ResponseEntity<ApiResponse<Void>> assignRoles(
            @PathVariable Long userId,
            @RequestBody Set<Long> roleIds) {

        userService.assignRoles(userId, roleIds);

        return ResponseEntity.ok(
                ApiResponse.success("Roles assigned successfully.")
        );
    }

    @DeleteMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('ASSIGN_ROLE')")
    @Operation(summary = "Remove role from user")
    public ResponseEntity<ApiResponse<Void>> removeRole(
            @PathVariable Long userId,
            @PathVariable Long roleId) {

        userService.removeRole(userId, roleId);

        return ResponseEntity.ok(
                ApiResponse.success("Role removed successfully.")
        );
    }
}