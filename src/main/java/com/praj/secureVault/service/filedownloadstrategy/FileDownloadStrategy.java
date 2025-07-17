package com.praj.secureVault.service.filedownloadstrategy;

import com.praj.secureVault.dto.FileDownloadResponseDTO;
import com.praj.secureVault.model.FileMetadata;

public interface FileDownloadStrategy {
    FileDownloadResponseDTO download(FileMetadata data) throws Exception;
}
