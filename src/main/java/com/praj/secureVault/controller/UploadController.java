package com.praj.secureVault.controller;

import com.praj.secureVault.dto.FileUploadResponseDTO;
import com.praj.secureVault.dto.HealthDTO;
import com.praj.secureVault.exception.FileEmptyException;
import com.praj.secureVault.service.FileUploadService;
import com.praj.secureVault.util.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/file")
public class UploadController {


    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    private final FileUploadService fileUploadService;

    public UploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }


    @PostMapping("/upload")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<ApiResponse<FileUploadResponseDTO>> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "strategy") String strategy, Principal principal)
            throws FileEmptyException, IOException {

        if(file.isEmpty()){
            throw new FileEmptyException("File is empty!!");
        }

        FileUploadResponseDTO dto = fileUploadService.uploadFile(file,strategy, principal);
        ApiResponse<FileUploadResponseDTO> response = ApiResponse.success(dto,"file uploaded to type " + dto.getStorageType());
        return ResponseEntity.ok(response);
    }


}
