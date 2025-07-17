package com.praj.secureVault.service.fileDecorators;

import com.praj.secureVault.dto.FileUploadResponseDTO;
import com.praj.secureVault.exception.FileEmptyException;
import com.praj.secureVault.service.fileUploadStrategy.FileUploadStrategy;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public abstract  class FileUploadStrategyDecorator implements  FileUploadStrategy {

    protected FileUploadStrategy wrappedStrategy;
    public FileUploadStrategyDecorator(FileUploadStrategy strategy){
        this.wrappedStrategy = strategy;
    }

    @Override
    public FileUploadResponseDTO upload(MultipartFile file , String username) throws IOException, FileEmptyException {
        return wrappedStrategy.upload(file,username);
    }
}
