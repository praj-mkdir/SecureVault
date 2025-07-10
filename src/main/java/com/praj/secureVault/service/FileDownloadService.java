package com.praj.secureVault.service;

import com.praj.secureVault.dto.FileDownloadResponseDTO;
import com.praj.secureVault.model.FileMetadata;
import com.praj.secureVault.repository.FileMetaDataRepository;
import com.praj.secureVault.service.fileDownloadStrategy.FileDownloadStrategy;
import com.praj.secureVault.service.fileDownloadStrategy.FileDownloadStrategyFactory;
import com.praj.secureVault.util.enums.DownloadStorageType;
import com.praj.secureVault.util.enums.UploadStorageType;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public class FileDownloadService {

    private final FileMetaDataRepository repository;
    private  final FileDownloadStrategyFactory factory;


    public FileDownloadService(FileMetaDataRepository repository, FileDownloadStrategyFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }


    public FileDownloadResponseDTO downloadFile(String strategyType, String id) throws Exception {
        DownloadStorageType type = DownloadStorageType.fromString(strategyType);
        FileDownloadStrategy strategy = factory.getDownloadStrategy(type);

        FileMetadata metadata = findFileById(id);
        return strategy.download(metadata);

    }

    public FileMetadata findFileById(String id) throws FileNotFoundException {
        return repository.findById(id).orElseThrow(FileNotFoundException::new);
    }




}

