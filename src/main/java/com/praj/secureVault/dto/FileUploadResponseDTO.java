package com.praj.secureVault.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileUploadResponseDTO {

    private String storageType;
    private String filePath;
    private String fileName;
    private String uploadedAt;
    private String checkSum;
    private Long filesize;
    private String fileID;


}
