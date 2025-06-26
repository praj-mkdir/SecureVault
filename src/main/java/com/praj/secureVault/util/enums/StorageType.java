package com.praj.secureVault.util.enums;


import com.praj.secureVault.exception.IllegalStorageTypeException;

import java.util.Arrays;

//Add the storageType as per your requirement here
public enum StorageType {
    LOCAL("local"),
    S3("s3"),
    AZURE("azure");

    private final String type;

    StorageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static StorageType fromString(String value) {
        return Arrays.stream(StorageType.values())
                .filter(t -> t.getType().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalStorageTypeException("Invalid storage type: " + value));
    }
}
