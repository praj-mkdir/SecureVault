package com.praj.secureVault.service.strategy;


import com.praj.secureVault.exception.IllegalStorageTypeException;
import com.praj.secureVault.util.enums.StorageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FileUploadStrategyFactory {

    private static final Logger log = LoggerFactory.getLogger(FileUploadStrategyFactory.class);
    private final Map<String, FileUploadStrategy> strategyMap;

    public  FileUploadStrategyFactory(Map<String, FileUploadStrategy> strategyMap){
        this.strategyMap = strategyMap;
    }


    public FileUploadStrategy getStrategy(StorageType type){

//        log.info("StartegyMap containig the stratgies autowired by springboot" + strategyMap.toString());

        FileUploadStrategy strategy = strategyMap.get(type.getType());
        log.info(strategy.toString());
        if(strategy == null ){
            throw new IllegalStorageTypeException("No strategy found for type  "+type);
        }
        return strategy;
    }


}
