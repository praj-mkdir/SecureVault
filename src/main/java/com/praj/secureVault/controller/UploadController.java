package com.praj.secureVault.controller;

import com.praj.secureVault.dto.FileUploadResponseDTO;
import com.praj.secureVault.dto.HealthDTO;
import com.praj.secureVault.exception.FileEmptyException;
import com.praj.secureVault.exception.IllegalStorageTypeException;
import com.praj.secureVault.service.FileUploadService;
import com.praj.secureVault.util.FileUtilFuncitons;
import com.praj.secureVault.util.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.event.ObjectChangeListener;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/file")
@Slf4j
public class UploadController {


//    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    private final FileUploadService fileUploadService;


    public UploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }


    @PostMapping("/upload")
//    @PreAuthorize("hasRole('user')")
    public ResponseEntity<ApiResponse<FileUploadResponseDTO>> upload(
            @RequestParam("file") MultipartFile file,
            Principal principal)
            throws FileEmptyException, IOException, IllegalStorageTypeException {

        if (file.isEmpty()) {
            throw new FileEmptyException("File is empty!!");
        }

        FileUploadResponseDTO dto = fileUploadService.uploadFile(file, principal);
        ApiResponse<FileUploadResponseDTO> response = ApiResponse.success(dto, "file uploaded to type " + dto.getStorageType());
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/s3-upload")
////    @PreAuthorize("hasRole('user')")
//    public ResponseEntity<ApiResponse<FileUploadResponseDTO>> uploadToS3(
//            @RequestParam("file") MultipartFile file,
//            Principal principal)
//            throws FileEmptyException, IOException, IllegalStorageTypeException {
//
//        if (file.isEmpty()) {
//            throw new FileEmptyException("File is empty!!");
//        }
//
//        FileUploadResponseDTO dto = fileUploadService.uploadFile(file, principal);
//        ApiResponse<FileUploadResponseDTO> response = ApiResponse.success(dto, "file uploaded to type " + dto.getStorageType());
//        return ResponseEntity.ok(response);
//    }

//    @PostMapping("pre-signed-url")
//    public ResponseEntity<Map<String, Object>> generateUrl(){
//
//    }


}
