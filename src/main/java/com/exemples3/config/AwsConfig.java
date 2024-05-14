package com.exemples3.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Value("${aws.acces-key-id}")
    private String accessKeyId;

    @Value("${aws.secret-access-key}")
    private String accessKeySecret;

    @Value("${aws.s3-region}")
    private String awsRegion;


    @Bean
    public AWSCredentials awsCredentials(){
        return new BasicAWSCredentials(accessKeyId, accessKeySecret);
    }

    @Bean
    public AmazonS3 amazonS3(){
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
                .withRegion(awsRegion)
                .build();
    }
}
