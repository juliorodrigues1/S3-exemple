package com.exemples3.config.Controller;

import com.amazonaws.services.s3.model.S3Object;
import com.exemples3.config.service.AwsService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadControlller {

    private final AwsService awsService;

    public UploadControlller(AwsService awsService) {
        this.awsService = awsService;
    }

    @PostMapping("upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        awsService.uploadFile(file);
        return "File uploaded successfully";
    }

    @GetMapping("download")
    public HttpEntity<byte[]> downloadFile(@PathParam(value = "file") String file) throws IOException {
        S3Object s3Object = awsService.getFile(file);
        String contentType = s3Object.getObjectMetadata().getContentType();
        var bytes = s3Object.getObjectContent().readAllBytes();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(contentType));
        header.setContentLength(bytes.length);

        return new HttpEntity<byte[]>(bytes, header);
    }
}
