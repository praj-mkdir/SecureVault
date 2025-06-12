package com.praj.secureVault.fileupload;

import com.praj.secureVault.health.dto.HealthDto;
import com.praj.secureVault.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/file")
public class UploadController {

    @GetMapping("/upload")
    public ResponseEntity<ApiResponse<HealthDto>> upload(){


        ApiResponse<HealthDto> response = ApiResponse.success(new HealthDto("up","Api service"),"Service is healthy");
        return ResponseEntity.ok(response);
    }


}
