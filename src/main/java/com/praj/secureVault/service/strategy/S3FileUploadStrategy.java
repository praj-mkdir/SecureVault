package com.praj.secureVault.service.strategy;

import com.praj.secureVault.dto.HealthDto;
import com.praj.secureVault.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


//todo implement this class afte configuring the s3 with springboot

@Component("s3UploadStrategy")
public class S3FileUploadStrategy implements FileUploadStrategy{

    //todo implement the s3 upload logic here

    @Override
    public ResponseEntity<?> upload(MultipartFile file, String username) throws IOException {
        //testing
        ApiResponse<String> response = ApiResponse.success("test","Service is healthy");
        return ResponseEntity.ok(response);
    }

}
