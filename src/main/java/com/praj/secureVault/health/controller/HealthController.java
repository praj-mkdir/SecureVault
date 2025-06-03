package com.praj.secureVault.health.controller;


import com.praj.secureVault.health.dto.HealthDto;
import com.praj.secureVault.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<HealthDto>> checkHealth(){

        HealthDto dto = new HealthDto("up","Api service");
        ApiResponse<HealthDto> response = ApiResponse.success(dto,"Service is healthy");
        return ResponseEntity.ok(response);
    }

}
