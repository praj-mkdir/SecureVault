package com.praj.secureVault.service;


import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor

public class S3PresignerService {

    private final AmazonS3 s3;

    @Value("${aws.bucket.name}")
    private String bucketName;

    public String generatePresignedUploadUrl(String objectKey, long expirationMillis){
        Date expiration = new Date(System.currentTimeMillis() + expirationMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName,objectKey)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(expiration);
        return s3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    public String generatePresignedDowloadUrl(String objectKey, long expirationMillis){
        Date expiration = new Date(System.currentTimeMillis() + expirationMillis);
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, objectKey)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        return s3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

}
