package com.example.storage_demo.attachment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.time.Duration;

import org.springframework.stereotype.Service;

import com.example.storage_demo.attachment.storage.KeyDetails;
import com.example.storage_demo.attachment.storage.StorageService;

@Service
public class AttachmentService {
    private final AttachmentRepository repo;
    private final StorageService storageService;

    public AttachmentService(AttachmentRepository repo, StorageService storageService) {
        this.repo = repo;
        this.storageService = storageService;
    }

    public Attachment createAttachment(InputStream file, String contentType, KeyDetails details) throws IOException {
        String key = storageService.storeFile(file, contentType, details);
        Attachment newAttachment = new Attachment();
        newAttachment.setKey(key);
        newAttachment.setOriginalFilename(details.getFilename());
        this.repo.save(newAttachment);
        return newAttachment;

    }

    public Optional<String> getAttachmentUrl(Long attachmentId) {
        Optional<Attachment> result = this.repo.findById(attachmentId);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(this.storageService.generatePresignedUrl(result.get().getKey(), Duration.ofMinutes(60L)));
    }

}
