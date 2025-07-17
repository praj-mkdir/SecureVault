package com.praj.secureVault.controller;

import com.praj.secureVault.dto.FileDownloadResponseDTO;
import com.praj.secureVault.model.FileMetadata;
import com.praj.secureVault.repository.FileMetaDataRepository;
import com.praj.secureVault.service.FileDownloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//Create FileDownloadController
//Add @PreAuthorize("hasRole('user') or hasRole('admin')")
//Check file ownership (match uploadedBy from metadata)
//Stream file from disk using InputStreamResource
//Return file as download with headers:
//Content-Disposition
//Content-Type
//Content-Length
//Optionally return checksum as a header (X-Checksum-SHA256)
//Log download action with MDC (username, traceId, filename)
// (Optional later) Add audit DB table entry

@RestController
@RequestMapping("/api/v1/file")
public class DownloadController {

    private final FileDownloadService downloadService;


    private static final Logger log = LoggerFactory.getLogger(DownloadController.class);
    private final FileMetaDataRepository repository;


    public DownloadController(FileDownloadService downloadService, FileMetaDataRepository repository) {
        this.downloadService = downloadService;
        this.repository = repository;
    }


    //    @PreAuthorize("hasRole('user') or hasRole('admin')  ")
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable String id, @RequestParam(defaultValue = "localDownload", value = "strategy") String strategy) throws Exception {

        log.info("DownloadController - inside");

        FileDownloadResponseDTO responseDTO = downloadService.downloadFile(strategy, id);
        if (!responseDTO.isRedirect()) {
            return responseDTO.getResponse();
        }
        return responseDTO.getResponse();
    }
}
