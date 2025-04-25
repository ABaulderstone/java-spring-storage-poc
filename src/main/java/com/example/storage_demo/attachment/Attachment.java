package com.example.storage_demo.attachment;

import com.example.storage_demo.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity()
@Table(name = "attachments")

public class Attachment extends BaseEntity {

    @Column
    String originalFilename;

    @Column(name = "s3_key")
    String key;

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
