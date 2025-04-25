package com.example.storage_demo.task;

import com.example.storage_demo.attachment.EntityAttachment;
import com.example.storage_demo.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity()
@Table(name = "tasks")
public class Task extends BaseEntity {

    @Column
    private String name;

    public Task() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
