package com.praj.secureVault.exception;

import com.praj.secureVault.util.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static  final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ApiErrorResponse> handleFileStorageException(FileStorageException ex, HttpServletRequest request){
        return buildErrorResponse(ex,request,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Builder function for APIERROR
    private  ResponseEntity<ApiErrorResponse> buildErrorResponse(Exception ex, HttpServletRequest request, HttpStatus status){
        String traceId = MDC.get("traceId");
        log.error("Exception [{}]: {}", traceId, ex.getMessage() );

        ApiErrorResponse error = new ApiErrorResponse.Builder()
                .status(status.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .traceId(traceId)
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

    }
}
