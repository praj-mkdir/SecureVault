package com.praj.secureVault.service.fileUploadStrategy;


import com.praj.secureVault.exception.IllegalStorageTypeException;
import com.praj.secureVault.service.fileDecorators.CheckSumDecorator;
import com.praj.secureVault.util.enums.UploadStorageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FileUploadStrategyFactory {

   private static final Logger log = LoggerFactory.getLogger(FileUploadStrategyFactory.class);
    private final Map<String, FileUploadStrategy> strategyMap;

    public  FileUploadStrategyFactory(Map<String, FileUploadStrategy> strategyMap){
        this.strategyMap = strategyMap;
    }

   @Value("${file.upload.enable-checkSum}")
   private boolean enableCheckSum;


    public FileUploadStrategy getStrategy(UploadStorageType type) throws IllegalStorageTypeException {


        FileUploadStrategy strategy = strategyMap.get(type.getType());
        FileUploadStrategy decorateStrategy = strategy;
        if(strategy == null ){
            throw new IllegalStorageTypeException("No strategy found for type  "+type);
        }

        if(enableCheckSum){
            decorateStrategy = new CheckSumDecorator(decorateStrategy);
        }
        return decorateStrategy;
    }


}
