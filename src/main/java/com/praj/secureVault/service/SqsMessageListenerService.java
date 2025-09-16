package com.praj.secureVault.service;

import com.praj.secureVault.dto.S3EventNotification;
import com.praj.secureVault.dto.S3EventRecord;
import com.praj.secureVault.model.FileMetadata;
import com.praj.secureVault.repository.FileMetaDataRepository;
import com.praj.secureVault.util.enums.FileStatus;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
                log.info(fileId + "file ID");

                Optional<FileMetadata> fileMetadata1 = repository.findById(fileId); //Making the sqs message idempotent
                if(fileMetadata1.get().getStatus().equals(FileStatus.UPLOADED)){
                    log.info("Message already saved");
                    return;
                }
                String originalFileName = generatedFileName.substring(generatedFileName.lastIndexOf('/') + 38);
                String fullGeneratedFileName = generatedFileName.substring(generatedFileName.lastIndexOf('/') + 1);
                int firstSlash = generatedFileName.indexOf('/', 1);          // after "/secureApp"
                int secondSlash = generatedFileName.indexOf('/', firstSlash + 1);

                String user = generatedFileName.substring(firstSlash + 1, secondSlash);
                FileMetadata metadata = FileMetadata.builder().id(fileId).fileName(originalFileName).generated_FileName(generatedFileName).storagePath("s3://" + bucketName).size(fileSizeInBytes / 1024).uploadedBy(parseUsernameFromKey(generatedFileName)).contentType(inferContentType(originalFileName)).traceId(MDC.get("traceId")).build();
                log.info("Saving file metadata for S3 upload: {}", originalFileName);
                Optional<FileMetadata> dbMetadata = repository.findById(fileId);
                log.info("dbMetaData -- recieved from db " + dbMetadata.toString());
                log.info("filemetada for saving --- from sqs " + metadata);
                if (dbMetadata.isPresent()) {
                    FileMetadata metadata1 = FileMetadata.builder().id(fileId).size(fileSizeInBytes / 1024).contentType(inferContentType(originalFileName)).storagePath("s3://" + bucketName).status(String.valueOf(FileStatus.UPLOADED)).traceId(MDC.get("traceId")).fileName(originalFileName).s3_Key("/" + generatedFileName).uploadedAt(eventTime).uploadedBy(user).generated_FileName(fullGeneratedFileName).build();
                    log.info("saving the file data -- " + metadata1.toString());
                    repository.save(metadata1);
                } else {
                    log.warn("File metadata with id {} not found for sqs update", fileId);
                }
                Optional<FileMetadata> dbMetadatas = repository.findById(fileId);
                log.info("testing -- db save" + dbMetadatas.toString());
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

