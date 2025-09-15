package com.praj.secureVault.service;


import com.praj.secureVault.dto.FileUploadResponseDTO;
import com.praj.secureVault.model.FileMetadata;
import com.praj.secureVault.repository.FileMetaDataRepository;
import com.praj.secureVault.util.AuthUtil;
import com.praj.secureVault.util.FileUtilFuncitons;
import com.praj.secureVault.util.enums.FileStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.internal.signing.DefaultS3Presigner;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    private final FileMetaDataRepository repository;

    public S3PresignerService(FileUtilFuncitons fileUtil, AuthUtil authUtil, FileMetaDataRepository repository) {
        this.fileUtil = fileUtil;
        this.authUtil = authUtil;
        this.repository = repository;
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
            String presignedUrl = presignedRequest.url().toExternalForm();
            Map<String, Object> result = new HashMap<>();
            result.put("presignedUrl", presignedUrl);
            result.put("key", key);
            result.put("storedFileName", storedFileName);
            result.put("httpMethod", presignedRequest.httpRequest().method().toString());
            result.put("expiresIn", Duration.ofMinutes(5).toMinutes() + " minutes");


                    FileMetadata metadata1 = FileMetadata.builder()
                    .id(storedFileName.substring(0,36))
                    .fileName(storedFileName.substring(37))
                    .storagePath(key)
                    .uploadedBy(authUtil.getCurrentUsername())
                    .uploadedAt(null)
                    .traceId(MDC.get("traceId"))
                    .generated_FileName(storedFileName)
                    .status(String.valueOf(FileStatus.PENDING))
                    .s3_Key(key)
                    .build();


            log.info("Saving the File pending state " + metadata1.toString()) ;
            repository.save(metadata1);
            return result;
        }

    }

    public Map<String, Object> generateDownloadPresignedUrl(String fileId){
        try(S3Presigner presigner = S3Presigner.create()){

            Optional<FileMetadata> metadata =  repository.findById(fileId);
            String key = metadata.get().getGenerated_FileName();
            log.info("key - " + key);

            GetObjectRequest objectRequest = GetObjectRequest.builder().bucket(bucketName).key(key).build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder().signatureDuration(Duration.ofMinutes(5)).getObjectRequest(objectRequest).build();
            PresignedGetObjectRequest presignedGetObjectRequest = presigner.presignGetObject(presignRequest);
            log.info("Presigned URL: [{}]", presignedGetObjectRequest.url().toString());
            log.info("HTTP method: [{}]", presignedGetObjectRequest.httpRequest().method());

            return Map.of("PresginedUrl" , presignedGetObjectRequest.url().toString());
        }
    }




}
