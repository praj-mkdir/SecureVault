package com.praj.secureVault.service.strategy;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadStrategy {
    void upload(MultipartFile file, String username) throws IOException;
}
