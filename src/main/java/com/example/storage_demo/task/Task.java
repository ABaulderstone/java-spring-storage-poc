package com.example.storage_demo.task;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.Where;

import com.example.storage_demo.attachment.Attachment;
import com.example.storage_demo.attachment.entity_attachment.EntityAttachment;
import com.example.storage_demo.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity()
@Table(name = "tasks")
public class Task extends BaseEntity {

    @Column
    private String name;

    @OneToMany
    @JoinColumn(name = "entity_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Where(clause = "entity_type = 'Task'")
    private List<EntityAttachment> entityAttachments;

    @Transient
    public List<Attachment> getAttachments() {
        return entityAttachments.stream()
                .map(EntityAttachment::getAttachment)
                .collect(Collectors.toList());
    }

    public Task() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EntityAttachment> getEntityAttachments() {
        return entityAttachments;
    }

    public void setEntityAttachments(List<EntityAttachment> entityAttachments) {
        this.entityAttachments = entityAttachments;
    }

}
