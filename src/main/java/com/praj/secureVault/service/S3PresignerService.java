package com.praj.secureVault.service;


import com.praj.secureVault.util.AuthUtil;
import com.praj.secureVault.util.FileUtilFuncitons;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.internal.signing.DefaultS3Presigner;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class S3PresignerService {

//    private final AmazonS3 s3;
    //todo test both the methods. go through the following url and understand upload and download functionality better
    // https://medium.com/@gurkanucar/spring-boot-s3-presigned-url-upload-download-f-eff588eda7e3

    @Value("${aws.bucket.name}")
    private String bucketName;

    private final FileUtilFuncitons fileUtil;
    private final AuthUtil authUtil;

    public S3PresignerService(FileUtilFuncitons fileUtil, AuthUtil authUtil) {
        this.fileUtil = fileUtil;
        this.authUtil = authUtil;
    }

    public Map<String, Object> generatePresignedUploadUrl(String originalFilename) {
        try (S3Presigner s3Presigner = S3Presigner.create()) {

            String storedFileName = fileUtil.generateStoredFileName(originalFilename);
            String userPath = fileUtil.resolveUserPath();

            Map<String, String> metadata = new HashMap<>();
            if (originalFilename != null && !originalFilename.isEmpty()) {
                metadata.put("original-filename", originalFilename);
            }
            String username = authUtil.getCurrentUsername();
            if (username != null && !username.isEmpty()) {
                metadata.put("uploaded-by", username);
            }
            String key = userPath + "/" + storedFileName;


            PutObjectRequest objectRequest = PutObjectRequest.builder().bucket(bucketName).metadata(metadata).key(key).build();
            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder().signatureDuration(Duration.ofMinutes(5)).putObjectRequest(objectRequest).build();
            PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
            String myURL = presignedRequest.url().toString();
            String presignedUrl = presignedRequest.url().toExternalForm();


//            log.info("Presigned URL to upload a file to: [{}]", myURL);
//            log.info("HTTP method: [{}]", presignedRequest.httpRequest().method());
            Map<String, Object> result = new HashMap<>();
            result.put("presignedUrl", presignedUrl);
            result.put("key", key);
            result.put("storedFileName", storedFileName);
            result.put("httpMethod", presignedRequest.httpRequest().method().toString());
            result.put("expiresIn", Duration.ofMinutes(5).toMinutes() + " minutes");

            return result;
        }

    }


}
