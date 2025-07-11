package com.praj.secureVault.service.fileUploadStrategy;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.praj.secureVault.dto.FileUploadResponseDTO;
import com.praj.secureVault.exception.FileEmptyException;
import com.praj.secureVault.util.FileUtilFuncitons;
import com.praj.secureVault.util.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;


//todo implement this class afte configuring the s3 with springboot

@Component("s3")
public class S3FileUploadStrategy implements FileUploadStrategy{

    private static final Logger log = LoggerFactory.getLogger(S3FileUploadStrategy.class);


    @Value("${aws.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;

    public S3FileUploadStrategy(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public FileUploadResponseDTO upload(MultipartFile file, String username) throws IOException, FileEmptyException {

        if (file.isEmpty()) {
            throw new FileEmptyException("Uploaded file is empty");
        }

        String fileName = FileUtilFuncitons.generateStoredFileName(file.getOriginalFilename());
        InputStream inputStream = file.getInputStream();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.addUserMetadata("uploaded-by", username);
        metadata.addUserMetadata("original-name", file.getOriginalFilename());
        metadata.setContentLength(file.getSize());

        PutObjectRequest request = new PutObjectRequest(bucketName,fileName,inputStream,metadata );
        s3Client.putObject(request);

        log.info("User '{}' upload file: '{}'", username, fileName);
        return FileUploadResponseDTO.builder()
                .storageType("S3")
                .filePath("s3://"+bucketName+"/"+fileName)
                .fileName(fileName)
                .uploadedAt(LocalDateTime.now().toString())
                .filesize(file.getSize())
                .contentType(file.getContentType())
                .build();

    }




}
