package com.praj.secureVault.service.strategy;

import com.praj.secureVault.controller.UploadController;
import com.praj.secureVault.dto.FileUploadResponseDTO;
import com.praj.secureVault.exception.FileEmptyException;
import com.praj.secureVault.util.enums.StorageType;
import com.praj.secureVault.util.response.ApiErrorResponse;
import com.praj.secureVault.util.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component("local")
public class LocalFileUploadStrategy implements FileUploadStrategy{



    @Value("${files.upload.dir}")
    private String uploadDir;

    private static final Logger log = LoggerFactory.getLogger(LocalFileUploadStrategy.class);

    @Override
    public FileUploadResponseDTO upload(MultipartFile file, String username) throws IOException, FileEmptyException {
        if (file.isEmpty()) {
            throw new FileEmptyException("Uploaded file is empty");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Path destination = Paths.get(uploadDir).resolve(fileName).normalize();

        Files.createDirectories(destination.getParent());

        try(InputStream inputStream = file.getInputStream()){
            Files.copy(inputStream,destination, StandardCopyOption.REPLACE_EXISTING);
        }

        log.info("User '{}' upload file: '{}'",username,fileName);

        return new FileUploadResponseDTO("Local", uploadDir.toString()) ;

    }
}
