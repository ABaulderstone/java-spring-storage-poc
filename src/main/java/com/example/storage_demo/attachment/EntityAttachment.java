package com.example.storage_demo.attachment;

import com.example.storage_demo.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "entity_attachments")
public class EntityAttachment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

    @Column
    private String entityType;

    @Column
    private Long entityId;

    public EntityAttachment() {
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

}
