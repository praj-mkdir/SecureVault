package com.praj.secureVault.health.controller;


import com.praj.secureVault.health.dto.HealthDto;
import com.praj.secureVault.util.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/public")
@Tag(name = "Public API's", description = "Health check endpoint ")
public class HealthController {

    @GetMapping("/health")
    @Operation(summary = "Get the health status of the application", description = "Returns if the service is healthy")
    public ResponseEntity<ApiResponse<HealthDto>> checkHealth(){

        HealthDto dto = new HealthDto("up","Api service");
        ApiResponse<HealthDto> response = ApiResponse.success(dto,"Service is healthy");
        return ResponseEntity.ok(response);
    }

}
