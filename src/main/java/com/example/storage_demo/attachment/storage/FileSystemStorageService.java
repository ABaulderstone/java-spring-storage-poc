package com.example.storage_demo.attachment.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({ "dev", "test" })
public class FileSystemStorageService implements StorageService {
    private final Path storageLocation;
    private final String baseUrl;

    public FileSystemStorageService(String storageDir, String baseUrl) {
        this.baseUrl = baseUrl;
        this.storageLocation = Paths.get(storageDir);
        try {
            Files.createDirectories(storageLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    @Override
    public String storeFile(InputStream inputStream, String contentType, String filename) throws IOException {
        String key = this.generateKey(filename);
        Path targePath = storageLocation.resolve(key);
        Files.copy(inputStream, targePath, StandardCopyOption.REPLACE_EXISTING);

        return key;
    }

    @Override
    public String generatePresignedUrl(String key, Duration expiration) {
        return baseUrl + "/" + key;
    }

    @Override
    public void deleteFile(String key) throws IOException {
        Path filePath = storageLocation.resolve(key);
        Files.deleteIfExists(filePath);
    }

}
