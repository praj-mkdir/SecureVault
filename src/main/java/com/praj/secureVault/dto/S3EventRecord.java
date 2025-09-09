package com.praj.secureVault.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class S3EventRecord {
    // Getter and Setter
    private S3Entity s3;

}
