package com.example.storage_demo.attachment;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.time.Duration;

import org.springframework.stereotype.Service;

import com.example.storage_demo.attachment.dto.AttachmentDto;
import com.example.storage_demo.attachment.entity_attachment.EntityAttachmentService;
import com.example.storage_demo.attachment.storage.KeyDetails;
import com.example.storage_demo.attachment.storage.StorageService;

@Service
public class AttachmentService {

    private final EntityAttachmentService entityAttachmentService;
    private final AttachmentRepository repo;
    private final StorageService storageService;

    public AttachmentService(AttachmentRepository repo, StorageService storageService,
            EntityAttachmentService entityAttachmentService) {
        this.repo = repo;
        this.storageService = storageService;
        this.entityAttachmentService = entityAttachmentService;
    }

    public Attachment createAttachment(InputStream file, String contentType, KeyDetails details) throws IOException {
        String key = storageService.storeFile(file, contentType, details);
        Attachment newAttachment = new Attachment();
        newAttachment.setKey(key);
        newAttachment.setOriginalFilename(details.getFilename());
        entityAttachmentService.create(newAttachment, details);
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

    public List<Attachment> getAttachmentsByIdList(List<Long> attachmentIds) {
        return this.repo.findAllById(attachmentIds);
    }

    // TODO: replace with model mapper
    public AttachmentDto mapAttachmentToDto(Attachment attachment) {
        AttachmentDto dto = new AttachmentDto();
        dto.setId(attachment.getId());
        dto.setOriginalFileName(attachment.getOriginalFilename());
        dto.setPresignedUrl(getAttachmentUrl(attachment.getId()).orElse(null));
        return dto;
    }

}
