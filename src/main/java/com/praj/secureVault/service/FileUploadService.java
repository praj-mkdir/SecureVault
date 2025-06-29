package com.praj.secureVault.service;

import com.praj.secureVault.dto.FileUploadResponseDTO;
import com.praj.secureVault.exception.FileEmptyException;
import com.praj.secureVault.service.fileStrategy.FileUploadStrategy;
import com.praj.secureVault.service.fileStrategy.FileUploadStrategyFactory;
import com.praj.secureVault.util.enums.StorageType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Service
public class FileUploadService {
    private  final FileUploadStrategyFactory factory;

    public FileUploadService(FileUploadStrategyFactory factory){
        this.factory = factory;
    }

    public FileUploadResponseDTO uploadFile(MultipartFile file, String strategyType, Principal principal) throws FileEmptyException, IOException {
        StorageType type = StorageType.fromString(strategyType);
        FileUploadStrategy strategy = factory.getStrategy(type);

      return  strategy.upload(file, principal.getName());


    }

}
