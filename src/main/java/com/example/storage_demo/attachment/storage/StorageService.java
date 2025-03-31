package com.example.storage_demo.attachment.storage;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.UUID;

public interface StorageService {
    String storeFile(InputStream inputStream, String contentType, KeyDetails details) throws IOException;

    String generatePresignedUrl(String key, Duration expiration);

    void deleteFile(String key) throws IOException;

    default String generateKey(KeyDetails details) {
        return String.format("%s/%s/%s-%s", details.getEntityName(), details.getEntityId(), UUID.randomUUID(),
                details.getFilename());
    }
}
