package com.praj.secureVault.service.filedownloadstrategy;

import com.praj.secureVault.dto.FileDownloadResponseDTO;
import com.praj.secureVault.exception.CustomFileNotFoundException;
import com.praj.secureVault.model.FileMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component("localDownload")
public class LocalFileDownloadStrategy implements FileDownloadStrategy {
    private static final Logger log = LoggerFactory.getLogger(LocalFileDownloadStrategy.class);


    @Override
    public FileDownloadResponseDTO download(FileMetadata metadata) throws Exception {
        log.info("Inside the LocalFileDownloadStrategy class");

        Path filePath = Paths.get(metadata.getStoragePath(), metadata.getGenerated_FileName());
        log.info(metadata.getFileName());

        if (!Files.exists(filePath)) {
            throw new CustomFileNotFoundException(String.format("File with id %s does not exists at path: %s ", metadata.getId(), metadata.getStoragePath()));
        }
        FileInputStream fileInputStream = new FileInputStream(filePath.toFile());

        Resource resource = new InputStreamResource(fileInputStream);

        ResponseEntity<Resource> response = ResponseEntity.ok().contentType(MediaType.parseMediaType(metadata.getContentType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + metadata.getFileName() + "\"").contentLength(Files.size(filePath)).body(resource);

        return FileDownloadResponseDTO.builder().isRedirect(false).response(response).build();
    }
}