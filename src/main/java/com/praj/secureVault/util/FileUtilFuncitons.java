package com.praj.secureVault.util;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Component
public class FileUtilFuncitons {


    @Value("${files.upload.dir}")
    public String directoryPath;

    @Value("${file.upload.strategy}")
    public String storageLocation;

    @Value("${aws.bucket.name}")
    public String bucketName;

    private final AuthUtil authUtil;

    public FileUtilFuncitons(AuthUtil authUtil) {
        this.authUtil = authUtil;
    }


    public String fileIdGen(){
        return UUID.randomUUID().toString();
    }

    public String generateStoredFileName(String originalFilename) {
        return fileIdGen() + "_" + StringUtils.cleanPath(originalFilename);
    }

    public String resolveUserPath() {
        log.info("storageLocation --- " + storageLocation);
        String realmName = authUtil.getRealmName();
        String userName = authUtil.getCurrentUsername();

        if ("local".equalsIgnoreCase(storageLocation)) {
            log.info(Paths.get(directoryPath, realmName, userName).toString());
            return Paths.get(directoryPath, realmName, userName).toString();
        } else if ("S3".equalsIgnoreCase(storageLocation)) {
            return realmName + "/" + userName;
        } else {
            throw new IllegalArgumentException("Unsupported storage location: " + storageLocation);

        }

    }
}
