package com.example.storage_demo.task.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

public class CreateTaskDto {
    @NotBlank
    private String name;

    private MultipartFile attachedFile;

    public CreateTaskDto() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getAttachedFile() {
        return attachedFile;
    }

    public void setAttachedFile(MultipartFile attachedFile) {
        this.attachedFile = attachedFile;
    }

}
