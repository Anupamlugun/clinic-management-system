package com.clinic.cms.exception.handler;

import com.clinic.cms.common.dto.v1.ApiResponse;
import com.clinic.cms.exception.custom.InvalidWorkflowTransitionException;
import com.clinic.cms.exception.custom.ResourceAlreadyExistsException;
import com.clinic.cms.exception.custom.ResourceNotFoundException;
import com.clinic.cms.exception.custom.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        log.warn("404 Not Found | {} | {}",
                request.getRequestURI(),
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handleAlreadyExists(
            ResourceAlreadyExistsException ex,
            HttpServletRequest request) {

        log.warn("409 Resource Already Exists | {} | {}",
                request.getRequestURI(),
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(InvalidWorkflowTransitionException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidTransition(
            InvalidWorkflowTransitionException ex,
            HttpServletRequest request) {

        log.warn("400 Invalid Workflow Transition | {} | {}",
                request.getRequestURI(),
                ex.getMessage());

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(
            ValidationException ex,
            HttpServletRequest request) {

        log.warn("400 Validation Failed | {} | {}",
                request.getRequestURI(),
                ex.getMessage());

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request) {

        log.warn("401 Unauthorized | {} | Invalid username or password",
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Invalid username or password"));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthorizationDenied(
            AuthorizationDeniedException ex,
            HttpServletRequest request) {

        log.warn("403 Forbidden | {} | {}",
                request.getRequestURI(),
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error(
                        "Access denied. You do not have permission to perform this action."
                ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request) {

        log.warn("403 Forbidden | {} | {}",
                request.getRequestURI(),
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(
            Exception ex,
            HttpServletRequest request) {

        log.error("500 Internal Server Error | {}", request.getRequestURI(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("An unexpected error occurred."));
    }
}