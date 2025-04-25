package com.example.storage_demo.attachment.entity_attachment;

import org.springframework.stereotype.Service;

import com.example.storage_demo.attachment.Attachment;
import com.example.storage_demo.attachment.storage.KeyDetails;

@Service
public class EntityAttachmentService {
    private EntityAttachmentRepository repo;

    public EntityAttachmentService(EntityAttachmentRepository repo) {
        this.repo = repo;
    }

    public EntityAttachment create(Attachment attachment, KeyDetails details) {
        EntityAttachment record = new EntityAttachment();
        record.setAttachment(attachment);
        record.setEntityId(details.getEntityId());
        record.setEntityType(details.getEntityName());
        this.repo.save(record);
        return record;
    }

}
