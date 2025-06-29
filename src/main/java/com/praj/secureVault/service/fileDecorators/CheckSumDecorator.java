package com.praj.secureVault.service.fileDecorators;

import com.praj.secureVault.controller.UploadController;
import com.praj.secureVault.dto.FileUploadResponseDTO;
import com.praj.secureVault.exception.FileEmptyException;
import com.praj.secureVault.service.fileStrategy.FileUploadStrategy;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class CheckSumDecorator extends FileUploadStrategyDecorator {

    private static final Logger log = LoggerFactory.getLogger(UploadController.class);


    public CheckSumDecorator(FileUploadStrategy strategy) {
        super(strategy);
    }

    @Override
    public FileUploadResponseDTO upload(MultipartFile file , String username) throws IOException, FileEmptyException {

        String checkSum = calculateSHA256CheckSum(file);
        log.info("Calculated SHA-256 checksum for file '{}': {}",
                file.getOriginalFilename(), checkSum);
        return wrappedStrategy.upload(file,username);
    }



    private String calculateSHA256CheckSum(MultipartFile file ) throws  IOException{
        String checkSum;
        try (InputStream is = file.getInputStream()) {
             checkSum = DigestUtils.sha256Hex(is);
        }
        return checkSum;

    }

}
