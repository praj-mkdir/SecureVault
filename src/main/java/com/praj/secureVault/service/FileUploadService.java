package com.praj.secureVault.service;

import com.praj.secureVault.service.strategy.FileUploadStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service

public class FileUploadService {
    private  final FileUploadStrategy fileUploadStrategy;


    public FileUploadService(FileUploadStrategy strategy){
        this.fileUploadStrategy = strategy;
    }

    //implement the upload functionality

}
