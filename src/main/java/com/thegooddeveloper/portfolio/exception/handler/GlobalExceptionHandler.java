package com.thegooddeveloper.portfolio.exception.handler;

import com.thegooddeveloper.portfolio.dto.generic.ApiResponse;
import jakarta.persistence.EntityNotFoundException;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

  /*
   * ===============================
   * 404 - No Resource Found
   * ===============================
   */
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ApiResponse<String>> handleNoResourceFound(NoResourceFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ApiResponse<>(
            false,
            "RESOURCE_NOT_FOUND",
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            ApiResponse.getCurrentPath()));
  }

  /*
   * ===============================
   * 400 - Validation Errors
   * ===============================
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {

    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(err -> err.getField() + ": " + err.getDefaultMessage())
        .toList();

    return ResponseEntity.badRequest()
        .body(new ApiResponse<>(
            false,
            "VALIDATION_FAILED",
            errors,
            HttpStatus.BAD_REQUEST.value(),
            ApiResponse.getCurrentPath()));
  }

  /*
   * ===============================
   * 404 - Entity Not Found
   * ===============================
   */
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiResponse<String>> handleEntityNotFound(EntityNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ApiResponse<>(
            false,
            ex.getMessage(),
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            ApiResponse.getCurrentPath()));
  }

  /*
   * ===============================
   * 400 - Bad Request (Custom)
   * ===============================
   */
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiResponse<String>> handleBadRequest(BadRequestException ex) {
    return ResponseEntity.badRequest()
        .body(new ApiResponse<>(
            false,
            ex.getMessage(),
            ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
            ApiResponse.getCurrentPath()));
  }

  /*
   * ===============================
   * 500 - Generic Error
   * ===============================
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<String>> handleGenericException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ApiResponse<>(
            false,
            "An unexpected error occurred",
            ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ApiResponse.getCurrentPath()));
  }
}
