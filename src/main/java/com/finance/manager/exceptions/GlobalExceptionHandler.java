package com.finance.manager.exceptions;

import com.finance.manager.models.responses.ApiDefaultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PotNotFoundException.class)
    public ResponseEntity<ApiDefaultResponse<Object>> handlePotNotFound(PotNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PotNameAlreadyUsedException.class)
    public ResponseEntity<ApiDefaultResponse<Object>> handlePotExits(PotNameAlreadyUsedException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiDefaultResponse<Object>> handleUserNotFound(UserNotFoundException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedPotOperationException.class)
    public ResponseEntity<ApiDefaultResponse<Object>> handleAccessDenied(AccessDeniedPotOperationException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiDefaultResponse<Object>> handleGeneric(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(TooManyAttemptsException.class)
    public ResponseEntity<ApiDefaultResponse<Object>> handleTooManyAttempts(TooManyAttemptsException ex) {
        return buildErrorResponse(HttpStatus.TOO_MANY_REQUESTS, ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiDefaultResponse<Object>> handleInvalidCredentials(InvalidCredentialsException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(BudgetNotFoundException.class)
    public ResponseEntity<ApiDefaultResponse<Object>> budgetNotFound(BudgetNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ApiDefaultResponse<Object>> invalidUser(InvalidUserException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiDefaultResponse<Object>> handleRuntime(RuntimeException ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred: " + ex.getMessage());
    }


    private ResponseEntity<ApiDefaultResponse<Object>> buildErrorResponse(HttpStatus status, String message) {
        ApiDefaultResponse<Object> response = ApiDefaultResponse.error(message);

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiDefaultResponse<Map<String, String>>> handleValidationErrors
            (MethodArgumentNotValidException ex) {
                Map<String, String> errors = ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .collect(Collectors.toMap(
                                FieldError::getField,
                                fieldError -> {
                                    String msg = fieldError.getDefaultMessage();;
                                    return msg != null ? msg.trim() : "Invalid value";
                                },
                                (existing, replacement) -> existing
                        ));


        ApiDefaultResponse<Map<String, String>> response = new ApiDefaultResponse<>(
                        false,
                        errors,
                        "Validation failed"
                );

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(response);
    }
}
