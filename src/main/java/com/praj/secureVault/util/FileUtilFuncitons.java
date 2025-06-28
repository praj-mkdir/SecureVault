package com.praj.secureVault.util;

import org.springframework.util.StringUtils;

import java.util.UUID;

public class FileUtilFuncitons {

    public static String generateStoredFileName(String originalFilename){
        String uuid = UUID.randomUUID().toString();
        return uuid + "_" + StringUtils.cleanPath(originalFilename);
    }
}
