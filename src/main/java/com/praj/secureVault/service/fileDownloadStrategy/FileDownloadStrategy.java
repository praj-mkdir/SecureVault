package com.praj.secureVault.service.fileDownloadStrategy;

import com.praj.secureVault.dto.FileDownloadResponseDTO;
import com.praj.secureVault.model.FileMetadata;

public interface FileDownloadStrategy {
    FileDownloadResponseDTO download(FileMetadata data) throws Exception;
}
