package com.exe01.backend.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

   // @Value("${cloud.aws.credentials.access-key}")
    private String awsAccessKey ="a";

   // @Value("${cloud.aws.credentials.secret-key}")
    private String awsSecretKey ="b";
    @Bean
    public AmazonS3 S3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                awsAccessKey   ,
                awsSecretKey
        );
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.AP_SOUTHEAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

}
