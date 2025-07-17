package com.praj.secureVault.exception;


public class FileEmptyException extends Exception {

    public FileEmptyException(String message) {
        super(message);

    }

    public FileEmptyException(String message, Throwable cause) {
        super(message, cause);

    }
}
