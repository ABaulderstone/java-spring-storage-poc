package com.example.storage_demo.attachment.entity_attachment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntityAttachmentRepository extends JpaRepository<EntityAttachment, Long> {
    List<EntityAttachment> findByEntityTypeAndEntityId(String entityType, Long entityId);
}
