package com.praj.secureVault.service;

import com.praj.secureVault.dto.FileUploadResponseDTO;
import com.praj.secureVault.exception.FileEmptyException;
import com.praj.secureVault.exception.IllegalStorageTypeException;
import com.praj.secureVault.model.FileMetadata;
import com.praj.secureVault.repository.FileMetaDataRepository;
import com.praj.secureVault.service.fileuploadstrategy.FileUploadStrategy;
//import com.praj.secureVault.service.fileuploadstrategy.FileUploadStrategyFactory;
import com.praj.secureVault.util.AuthUtil;
import com.praj.secureVault.util.enums.UploadStorageType;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Service
@Slf4j
public class FileUploadService {
//    private  final FileUploadStrategyFactory factory;
    private final FileMetaDataRepository repository;
    private final FileUploadStrategy strategy;

    public FileUploadService(FileMetaDataRepository repository, FileUploadStrategy strategy){
        this.repository = repository;
        this.strategy = strategy;
    }

    public FileUploadResponseDTO uploadFile(MultipartFile file, Principal principal) throws FileEmptyException, IOException, IllegalStorageTypeException {
//        UploadStorageType type = UploadStorageType.fromString(strategyType);
//        FileUploadStrategy strategy = factory.getStrategy(type);

      FileUploadResponseDTO response =  strategy.upload(file, principal.getName());
      FileMetadata savedData = saveFileMetadata(response);
      response.setFileID(savedData.getId());
      return response;

    }

    private FileMetadata saveFileMetadata(FileUploadResponseDTO response){
        FileMetadata metadata = FileMetadata.builder()
                .fileName(response.getFileName())
                .size(response.getFilesize())
                .storagePath(response.getFilePath())
                .uploadedBy(AuthUtil.getCurrentUsername())
                .uploadedAt(response.getUploadedAt())
                .size(response.getFilesize()/1024)
                .checksumSha256(response.getCheckSum())
                .traceId(MDC.get("traceId"))
                .contentType(response.getContentType())
                .generated_FileName(response.getGenerateFileName())
                .build();
        log.info("Saving the file Metadata ");
        return repository.save(metadata);

    }

}
