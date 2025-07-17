package com.praj.secureVault.service;

import com.praj.secureVault.dto.FileDownloadResponseDTO;
import com.praj.secureVault.exception.CustomFileNotFoundException;
import com.praj.secureVault.model.FileMetadata;
import com.praj.secureVault.repository.FileMetaDataRepository;
import com.praj.secureVault.service.filedownloadstrategy.FileDownloadStrategy;
import com.praj.secureVault.service.filedownloadstrategy.FileDownloadStrategyFactory;
import com.praj.secureVault.util.enums.DownloadStorageType;
import org.springframework.stereotype.Service;

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

    public FileMetadata findFileById(String id) throws CustomFileNotFoundException {
        return repository.findById(id).orElseThrow(
                () -> new CustomFileNotFoundException(String.format("File with id %s does not exists",id))
        );
    }




}

