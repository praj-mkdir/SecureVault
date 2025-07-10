package com.praj.secureVault.controller;

import com.praj.secureVault.dto.FileDownloadResponseDTO;
import com.praj.secureVault.service.FileDownloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    private  final FileDownloadService downloadService;


    private static final Logger log = LoggerFactory.getLogger(DownloadController.class);


    public DownloadController(FileDownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @PreAuthorize("hasRole('user') or hasRole('admin')  ")
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable String id, @RequestParam(value = "strategy") String strategy) throws Exception {

        //todo check uploaded user should be able to download the file or the admin .
        log.info("DownloadController - inside");

        FileDownloadResponseDTO responseDTO = downloadService.downloadFile(strategy,id);
//        ApiResponse<FileDownloadResponseDTO> response = ApiResponse.success(responseDTO,"File Downloaded successfully!");
       if(responseDTO.isRedirect()){
           return responseDTO.getResponse();
       }
        return responseDTO.getResponse();
    }
}
