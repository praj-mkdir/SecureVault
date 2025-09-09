package com.praj.secureVault.service;

import com.praj.secureVault.dto.S3EventNotification;
import com.praj.secureVault.dto.S3EventRecord;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SqsMessageListenerService {

   @SqsListener("s3eventsQ")
    public void receiveMessage(S3EventNotification notification){
        for (S3EventRecord record : notification.getRecords()) {
            String bucketName = record.getS3().getBucket().getName();
            String fileName = record.getS3().getObject().getKey();
            log.info("File Event: Bucket: {}, File: {}", bucketName, fileName);
        }
    }
    }

