package com.praj.secureVault.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class FileUploadResponseDTO {

    private String storageType;
    private String filePath;
}
