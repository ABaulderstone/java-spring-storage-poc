package com.example.storage_demo.task.dto;

import java.util.List;

import com.example.storage_demo.attachment.dto.AttachmentDto;
import com.example.storage_demo.common.BaseEntity;

public class TaskDto extends BaseEntity {
    private String name;
    private List<AttachmentDto> attachmentDtos;

    public TaskDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttachmentDtos(List<AttachmentDto> attachmentDtos) {
        this.attachmentDtos = attachmentDtos;
    }

    public List<AttachmentDto> getAttachmentDtos() {
        return attachmentDtos;
    }

}
