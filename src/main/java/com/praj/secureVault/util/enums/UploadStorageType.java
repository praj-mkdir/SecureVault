package com.praj.secureVault.util.enums;


import com.praj.secureVault.exception.IllegalStorageTypeException;
import lombok.Getter;

import java.util.Arrays;

//Add the storageType as per your requirement here
@Getter
public enum UploadStorageType {
    LOCAL("local"),
    S3("s3"),
    AZURE("azure");

    private final String type;

    UploadStorageType(String type) {
        this.type = type;
    }

    public static UploadStorageType fromString(String value) throws IllegalStorageTypeException {
        return Arrays.stream(UploadStorageType.values())
                .filter(t -> t.getType().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalStorageTypeException("Invalid storage type: " + value));
    }
}
