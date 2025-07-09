package com.praj.secureVault.dto;

import lombok.*;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileDownloadResponseDTO {
    private boolean isRedirect;
    private ResponseEntity<?> response;

}
