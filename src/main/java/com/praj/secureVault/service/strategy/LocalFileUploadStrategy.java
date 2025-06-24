package com.praj.secureVault.service.strategy;

import com.praj.secureVault.util.response.ApiErrorResponse;
import com.praj.secureVault.util.response.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class LocalFileUploadStrategy implements FileUploadStrategy{


    //todo implement the local file upload stratgery while testing in local and all.

    @Value("${files.upload.dir}")
    private String uploadDir;


    @Override
    public ResponseEntity<?> upload(MultipartFile file, String username) throws IOException {

        return ResponseEntity.ok().build();

    }
}
