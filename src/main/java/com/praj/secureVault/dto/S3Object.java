package com.praj.secureVault.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class S3Object {
    private String key;
    private long size;

    // Getters and Setters
    public String getKey() {
        return key;
    }



    public void setKey(String key) {
        this.key = key;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
