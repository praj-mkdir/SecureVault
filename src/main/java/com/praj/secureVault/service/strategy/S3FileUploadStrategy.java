package com.praj.secureVault.service.strategy;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


//todo implement this class afte configuring the s3 with springboot

@Component("s3UploadStrategy")
public class S3FileUploadStrategy implements FileUploadStrategy{

    //todo implement the s3 upload logic here

    @Override
    public void upload(MultipartFile file, String username) throws IOException {

    }

}
