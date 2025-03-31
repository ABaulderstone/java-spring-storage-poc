package com.example.storage_demo.attachment.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Date;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
@Profile({ "prod", "staging" })
public class S3StorageService implements StorageService {
    private final AmazonS3 s3Client;
    private final String bucket;

    public S3StorageService(AmazonS3 s3Client, String bucket) {
        this.s3Client = s3Client;
        this.bucket = bucket;
    }

    @Override
    public String storeFile(InputStream inputStream, String contentType, KeyDetails details) throws IOException {
        String key = this.generateKey(details);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);

        s3Client.putObject(key, key, inputStream, metadata);
        return key;
    }

    @Override
    public String generatePresignedUrl(String key, Duration expiration) {
        Date exp = new Date();
        long expTimeMillis = exp.getTime();
        expTimeMillis += expiration.toMillis();
        exp.setTime(expTimeMillis);

        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(this.bucket, key)
                .withMethod(HttpMethod.GET).withExpiration(exp);
        URL url = s3Client.generatePresignedUrl(urlRequest);
        return url.toString();

    }

    @Override
    public void deleteFile(String key) throws IOException {
        s3Client.deleteObject(this.bucket, key);
    }

}
