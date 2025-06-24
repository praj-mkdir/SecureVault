package com.praj.secureVault.controller;

import com.praj.secureVault.dto.HealthDto;
import com.praj.secureVault.exception.FileEmptyException;
import com.praj.secureVault.util.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/file")
public class UploadController {


    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    @PostMapping("/upload")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<ApiResponse<?>> upload(@RequestParam("file") MultipartFile file) throws FileEmptyException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(file.isEmpty()){
//            throw new FileEmptyException("File is empty!!");
//        }

        ApiResponse<HealthDto> response = ApiResponse.success(new HealthDto("up","Api service"),"Service is healthy");
        return ResponseEntity.ok(response);
    }


}
