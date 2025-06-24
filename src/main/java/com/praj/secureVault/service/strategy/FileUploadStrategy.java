package com.praj.secureVault.service.strategy;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadStrategy {
    ResponseEntity<?> upload(MultipartFile file, String username) throws IOException;
}
