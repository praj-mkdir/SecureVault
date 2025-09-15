package com.praj.secureVault.util.enums;


import lombok.Getter;

@Getter
public enum FileStatus {
    PENDING,
    SCANNING,
    UPLOADED,
    FAILED,
    READY,
    FAILED_SCAN,
    FAILED_UPLOAD
}
