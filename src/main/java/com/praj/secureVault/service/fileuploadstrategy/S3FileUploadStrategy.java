package com.praj.secureVault.service.fileuploadstrategy;


import com.praj.secureVault.dto.FileUploadResponseDTO;
import com.praj.secureVault.exception.FileEmptyException;
import com.praj.secureVault.util.FileUtilFuncitons;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Component("s3")
@Slf4j
@ConditionalOnProperty(name = "file.upload.strategy", havingValue = "s3")
public class S3FileUploadStrategy implements FileUploadStrategy{



    @Value("${aws.bucket.name}")
    private String bucketName;

    private final S3Client s3Client;
    private final FileUtilFuncitons fileUtil;
    public S3FileUploadStrategy(S3Client  s3Client, FileUtilFuncitons fileUtil) {
        this.s3Client = s3Client;
        this.fileUtil = fileUtil;
    }



    @Override
    public FileUploadResponseDTO upload(MultipartFile file, String username) throws IOException, FileEmptyException {

        if (file.isEmpty()) {
            throw new FileEmptyException("Uploaded file is empty");
        }
        String fileName = fileUtil.generateStoredFileName(file.getOriginalFilename());
        String key = fileUtil.resolveUserPath()+"/"+fileName;

        InputStream inputStream = file.getInputStream();

        Map<String, String> metadata = new HashMap<>();
        metadata.put("file-type" ,file.getContentType());
            metadata.put("uploaded-by", username);
            metadata.put("original-name", file.getOriginalFilename());
            metadata.put("file-length", String.valueOf(file.getSize()));
//        PutObjectRequest request = PutObjectRequest.builder(bucketName,fileName,inputStream,metadata );
        PutObjectRequest request = PutObjectRequest.builder().bucket(bucketName).key(key).metadata(metadata).build();

        s3Client.putObject(request,RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        log.info("User '{}' upload file: '{}'", username, fileName);
        return FileUploadResponseDTO.builder()
                .storageType("S3")
                .filePath("s3://"+bucketName+"/"+key)
                .fileName(fileName)
                .uploadedAt(LocalDateTime.now().toString())
                .filesize(file.getSize())
                .contentType(file.getContentType())
                .build();

    }
}
