package com.praj.secureVault.service.fileDownloadStrategy;

import com.praj.secureVault.dto.FileDownloadResponseDTO;
import com.praj.secureVault.dto.FileUploadResponseDTO;
import com.praj.secureVault.model.FileMetadata;
import com.praj.secureVault.service.fileUploadStrategy.FileUploadStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component("localDownload")
public class LocalFileDownloadStrategy implements FileDownloadStrategy {
    private static final Logger log = LoggerFactory.getLogger(LocalFileDownloadStrategy.class);


    @Override
    public FileDownloadResponseDTO download(FileMetadata metadata) throws Exception {
        log.info("Inside the LocalFileDownloadStrategy class");

        Path filePath = Paths.get(metadata.getStoragePath());

        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found at path: " + filePath.toString());
        }

        Resource resource = new UrlResource(filePath.toUri());

        ResponseEntity<Resource> response = ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(metadata.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + metadata.getFileName() + "\"")
                .body(resource);

        return FileDownloadResponseDTO.builder().isRedirect(false).response(response).build();
    }
}