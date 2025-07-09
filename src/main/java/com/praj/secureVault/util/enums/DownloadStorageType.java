package com.praj.secureVault.util.enums;

import com.praj.secureVault.exception.IllegalStorageTypeException;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum DownloadStorageType {
    LOCAL("localDownload"),
    S3("s3Download"),
    AZURE("azureDownload");

    private final String type;
    DownloadStorageType(String type) {
        this.type = type;
    }

    public static DownloadStorageType fromString(String value) throws IllegalStorageTypeException {
        return Arrays.stream(DownloadStorageType.values())
                .filter(t -> t.getType().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalStorageTypeException("Invalid storage type: " + value));
    }
}
