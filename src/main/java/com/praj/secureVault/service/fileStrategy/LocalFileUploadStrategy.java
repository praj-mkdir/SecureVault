package com.praj.secureVault.service.fileStrategy;

import com.praj.secureVault.dto.FileUploadResponseDTO;
import com.praj.secureVault.exception.FileEmptyException;
import com.praj.secureVault.util.FileUtilFuncitons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Component("local")
public class LocalFileUploadStrategy implements FileUploadStrategy {


    @Value("${files.upload.dir}")
    private String uploadDir;

    private static final Logger log = LoggerFactory.getLogger(LocalFileUploadStrategy.class);

    @Override
    public FileUploadResponseDTO upload(MultipartFile file, String username) throws IOException, FileEmptyException {
        if (file.isEmpty()) {
            throw new FileEmptyException("Uploaded file is empty");
        }

        String fileName = FileUtilFuncitons.generateStoredFileName(file.getOriginalFilename());

        Path destination = Paths.get(uploadDir).resolve(fileName).normalize();

        Files.createDirectories(destination.getParent());

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
        }

        log.info("User '{}' upload file: '{}'", username, fileName);
        return FileUploadResponseDTO.builder()
                .storageType("Local")
                .filePath(uploadDir)
                .fileName(file.getOriginalFilename())
                .uploadedAt(LocalDateTime.now().toString())
                .filesize(file.getSize())
                .build();

    }
}
