package com.exemples3.config.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@Service
public class AwsService {

    @Value("${aws.bucket-name}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    public AwsService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void uploadFile(MultipartFile fileUpload) {
        File file = toFile(fileUpload);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, file.getName(), file);
        amazonS3.putObject(putObjectRequest);

        file.delete();
    }

    public S3Object getFile(String fileName) throws IOException {
       return amazonS3.getObject(bucketName, fileName);

    }


    private File toFile(MultipartFile fileUpload) {
        File file = new File(fileUpload.getOriginalFilename());

        try (InputStream inputStream = fileUpload.getInputStream()) {
            Files.copy(inputStream, file.toPath());
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
