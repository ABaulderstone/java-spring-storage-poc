package com.example.storage_demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
@Profile({ "prod, staging" })
public class S3Config {
    @Bean
    public AmazonS3 amazonS3(@Value("${aws.region:us-east-1}") String region,
            @Value("${aws.s3.bucket}") String bucket) {
        AmazonS3 client = AmazonS3ClientBuilder.standard().withRegion(region).build();

        if (!client.doesBucketExistV2(bucket)) {
            client.createBucket(bucket);
        }
        return client;
    }
}
