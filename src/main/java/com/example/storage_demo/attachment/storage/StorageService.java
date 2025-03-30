package com.example.storage_demo.attachment.storage;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.UUID;

public interface StorageService {
    String storeFile(InputStream inputStream, String contentType, String filename) throws IOException;

    String generatePresignedUrl(String key, Duration expiration);

    void deleteFile(String key) throws IOException;

    default String generateKey(String filename) {
        return UUID.randomUUID() + "-" + filename;
    }
}
