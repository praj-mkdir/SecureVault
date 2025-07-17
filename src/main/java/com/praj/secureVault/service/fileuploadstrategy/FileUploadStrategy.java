package com.praj.secureVault.service.fileuploadstrategy;

import com.praj.secureVault.dto.FileUploadResponseDTO;
import com.praj.secureVault.exception.FileEmptyException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadStrategy {
    FileUploadResponseDTO upload(MultipartFile file, String username) throws IOException, FileEmptyException;
}
