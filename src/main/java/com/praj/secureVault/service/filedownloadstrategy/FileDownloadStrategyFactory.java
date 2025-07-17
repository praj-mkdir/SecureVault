package com.praj.secureVault.service.filedownloadstrategy;

import com.praj.secureVault.exception.IllegalStorageTypeException;
import com.praj.secureVault.util.enums.DownloadStorageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FileDownloadStrategyFactory {
    private static final Logger log = LoggerFactory.getLogger(FileDownloadStrategyFactory.class);

    private final Map<String,FileDownloadStrategy> strategyMap;


    public FileDownloadStrategyFactory(Map<String, FileDownloadStrategy> strategyMap) {
        this.strategyMap = strategyMap;
    }

    public FileDownloadStrategy getDownloadStrategy(DownloadStorageType type) throws IllegalStorageTypeException {
        FileDownloadStrategy strategy = strategyMap.get(type.getType());
        if(strategy == null ){
            throw new IllegalStorageTypeException("No strategy found for type  "+type);
        }

        return strategy;

    }
}
