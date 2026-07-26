package com.clinic.cms.auth.controller.v1;

import com.clinic.cms.auth.dto.v1.request.LoginRequest;
import com.clinic.cms.auth.dto.v1.response.LoginResponse;
import com.clinic.cms.auth.dto.v1.response.UserResponse;
import com.clinic.cms.auth.service.AuthService;
import com.clinic.cms.common.dto.v1.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication and authorization APIs")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Login",
            description = "Authenticate user and return access and refresh tokens."
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Login successful.",
                        authService.login(request)
                )
        );
    }

    @Operation(summary = "Refresh access token")
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        String refreshToken = authorizationHeader.substring(7);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Token refreshed successfully.",
                        authService.refreshToken(refreshToken)
                )
        );
    }

    @Operation(
            summary = "Logout",
            description = "Logout the currently authenticated user."
    )
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        String token = authorizationHeader.substring(7);

        authService.logout(token);

        return ResponseEntity.ok(
                ApiResponse.success("Logout successful.")
        );
    }

    @Operation(
            summary = "Current User",
            description = "Retrieve the profile of the currently authenticated user."
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> me() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "User profile fetched successfully.",
                        authService.me()
                )
        );
    }

}