package com.praj.secureVault.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class S3Entity {
    // Getters and Setters
    private S3Bucket bucket;
    private S3Object object;

}
