package com.praj.secureVault.util.response;

import java.time.Instant;

public class ApiResponse<T>{

    private String status;
    private  T data ;
    private String message;
    private  String timeStamp;

    public ApiResponse(String status, T data, String message, String timeStamp) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public static <T> ApiResponse<T> success(T data, String message){
        return new ApiResponse<>("success",data, message, Instant.now().toString());
    }

    public static <T> ApiResponse<T> error(String message){
        return new ApiResponse<>("error",null,message,Instant.now().toString());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
