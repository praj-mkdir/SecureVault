package com.praj.secureVault.service;

import com.praj.secureVault.dto.S3EventNotification;
import com.praj.secureVault.dto.S3EventRecord;
import com.praj.secureVault.model.FileMetadata;
import com.praj.secureVault.repository.FileMetaDataRepository;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SqsMessageListenerService {
    private final FileMetaDataRepository repository;

    public SqsMessageListenerService(FileMetaDataRepository repository) {
        this.repository = repository;
    }

    @SqsListener("s3eventsQ")
    public void receiveMessage(S3EventNotification notification) {

        try {
            if (notification == null || notification.getRecords() == null) {
                log.warn("Returned a null or empty notifications from S3");
                return;
            }
            for (S3EventRecord record : notification.getRecords()) {
                String bucketName = record.getS3().getBucket().getName();
                String generatedFileName = record.getS3().getObject().getKey();
                long fileSizeInBytes = record.getS3().getObject().getSize();
                String eventTime = record.getEventTime();

                String fileId = generatedFileName.substring(generatedFileName.lastIndexOf('/') + 1, generatedFileName.lastIndexOf('/') + 37);
                log.info("generatedFile" +generatedFileName);
                String originalFileName = generatedFileName.substring(generatedFileName.lastIndexOf('/') + 38);
                log.info(originalFileName+"afafa originalfilename");
                FileMetadata metadata = FileMetadata.builder().id(fileId).fileName(originalFileName).generated_FileName(generatedFileName).storagePath("s3://" + bucketName).size(fileSizeInBytes / 1024).uploadedBy(parseUsernameFromKey(generatedFileName)).contentType(inferContentType(originalFileName)).traceId(MDC.get("traceId")).build();
                log.info("Saving file metadata for S3 upload: {}", originalFileName);

                repository.save(metadata);
            }

        } catch (Exception e) {
            log.error("An error while processing SQS message", e);
        }

    }

    private String inferContentType(String fileName) {
        // Use Spring's MediaTypeFactory to guess the content type from the file extension
        return MediaTypeFactory.getMediaType(fileName).map(MediaType::toString) // If found, convert it to a String (e.g., "image/png")
                .orElse("application/octet-stream"); // Default if the extension is unknown
    }

    // Helper method to extract username from the key, based on your example
    private String parseUsernameFromKey(String key) {
        // Key: "secureApp/praj-user/UUID_test.png"
        String[] parts = key.split("/");
        if (parts.length > 1) {
            return parts[1]; // Returns "praj-user"
        }
        return "unknown"; // Fallback
    }


}

