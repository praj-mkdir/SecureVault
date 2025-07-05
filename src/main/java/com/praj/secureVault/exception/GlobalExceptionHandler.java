package com.praj.secureVault.exception;

import com.praj.secureVault.util.response.ApiErrorResponse;
import com.praj.secureVault.util.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    String traceId = MDC.get("traceId");

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ApiErrorResponse> handleFileStorageException(FileStorageException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //File emtpy exception
    @ExceptionHandler(FileEmptyException.class)
    public ResponseEntity<ApiErrorResponse> handleFileEmtpyExcption(FileEmptyException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }


    //Authentication Exception and Inalid JWT
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handlAuthenticationExecption(AuthenticationException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.UNAUTHORIZED);
    }

    //AccessDenied Exception
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.FORBIDDEN);
    }

    //IllegalStorageTypeExecption
    @ExceptionHandler(IllegalStorageTypeException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalStorageTypeException(IllegalStorageTypeException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(NoHandlerFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(
                new RuntimeException("Resource not found: " + request.getRequestURI()),
                request,
                HttpStatus.NOT_FOUND
        );
    }

//    //Catch general Exception  as fallback
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiErrorResponse> handleUnhandledExceptions(
//            Exception ex, HttpServletRequest request) {
//
//        log.error("Error [{}]: {} at {}", traceId, ex.getMessage(), request.getRequestURI());
//
//        return buildErrorResponse(
//                new RuntimeException("Something went wrong. Please contact support."),
//                request,
//                HttpStatus.INTERNAL_SERVER_ERROR
//        );
//    }

    //Builder function for APIERROR
    private ResponseEntity<ApiErrorResponse> buildErrorResponse(Exception ex, HttpServletRequest request, HttpStatus status) {
        String traceId = MDC.get("traceId");
        log.error("Exception [{}]: {}", traceId, ex.getMessage());

        ApiErrorResponse error = new ApiErrorResponse.Builder()
                .status(status.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .traceId(traceId)
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(error);

    }
}
