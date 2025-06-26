package com.praj.secureVault.exception;




public class IllegalStorageTypeException extends RuntimeException{
    public IllegalStorageTypeException(String message){
        super(message);
    }

    public IllegalStorageTypeException(String message, Throwable cause){
        super(message,cause);
    }

}
